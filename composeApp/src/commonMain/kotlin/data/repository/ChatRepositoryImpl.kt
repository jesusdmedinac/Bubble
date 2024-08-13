package data.repository

import data.mapper.toData
import data.mapper.toDomain
import data.remote.AnalyticsAPI
import data.remote.model.DataBody
import data.remote.ChatMessagesAPI
import data.remote.GeminiAPI
import data.remote.model.ContentItem
import data.remote.model.DataMessage
import data.remote.model.GeminiContent
import domain.ChatRepository
import domain.UserRepository
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
            .toDomain()

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
                val dataBody = content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?.let { Json.decodeFromString<DataBody>(it) }
                if (dataBody != null) {
                    Message(
                        id = geminiContent.contents.size + 1,
                        author = content.role,
                        body = dataBody.toDomain()
                    )
                        .apply { analyticsAPI.sendChatResponseEvent(this.toData()) }
                        .let { bubbleMessage -> saveMessage(bubbleMessage) }
                        .let { Result.success(Unit) }
                } else {
                    Result.failure(Exception("No dataMessages found"))
                }
            }
            ?: Result.failure(Exception("No dataMessages found"))

    override suspend fun saveMessage(message: Message): Result<Unit> =
        chatMessagesAPI.saveMessage(message.toData())
}