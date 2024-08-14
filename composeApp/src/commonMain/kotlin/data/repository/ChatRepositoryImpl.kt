package data.repository

import data.mapper.toData
import data.mapper.toDomain
import data.remote.AnalyticsAPI
import data.remote.model.DataBody
import data.remote.ChatMessagesAPI
import data.remote.GeminiAPI
import data.remote.model.ContentItem
import data.remote.model.GeminiContent
import domain.repository.ChatRepository
import domain.repository.UserRepository
import domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class ChatRepositoryImpl(
    private val geminiAPI: GeminiAPI,
    private val userRepository: UserRepository,
    private val chatMessagesAPI: ChatMessagesAPI,
    private val analyticsAPI: AnalyticsAPI,
) : ChatRepository {
    override suspend fun initChat() {
        userRepository
            .initUser()
            .onFailure {
                it.printStackTrace()
            }
    }

    override suspend fun getMessages(): Result<Flow<List<Message>>> =
        chatMessagesAPI.getChatMessages()
            .map { flow ->
                flow.map { dataMessages ->
                    dataMessages.messages.map { it.toDomain() }
                }
            }

    override suspend fun sendMessage(message: Message): Result<Unit> =
        saveMessage(message)
            .let { getMessages() }
            .fold(
                onSuccess = { it },
                onFailure = { null }
            )
            ?.firstOrNull()
            ?.map {
                ContentItem(
                    role = it.author,
                    parts = listOf(
                        data.remote.model.ContentPart(
                            text = it.body.message ?: ""
                        )
                    )
                )
            }
            ?.reversed()
            ?.let { GeminiContent(contents = it) }
            ?.let { geminiContent ->
                val auth = userRepository.getUser().getOrNull()?.id ?: ""
                val generatedContentResponse = geminiAPI.generateContent(auth, geminiContent)
                val content = generatedContentResponse
                    .candidates
                    .firstOrNull()
                    ?.content
                runCatching {
                    content
                        ?.parts
                        ?.firstOrNull()
                        ?.text
                        ?.let { Json.decodeFromString<DataBody>(it) }
                        ?: throw Exception("No messages found")
                }
                    .map { dataBody ->
                        Message(
                            id = geminiContent.contents.size + 1,
                            author = content?.role ?: "model",
                            body = dataBody.toDomain()
                        )
                            .apply { analyticsAPI.sendChatResponseEvent(this.toData()) }
                            .let { bubbleMessage -> saveMessage(bubbleMessage) }
                        Unit
                    }
            }
            ?: Result.failure(Exception("No messages found"))

    override suspend fun saveMessage(message: Message): Result<Unit> =
        chatMessagesAPI.saveMessage(message.toData())
}