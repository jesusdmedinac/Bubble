package domain

import data.remote.Analytics
import data.remote.Body
import data.remote.GeminiAPI
import data.remote.ChatMessagesAPI
import data.remote.Message
import data.remote.model.ContentItem
import data.remote.model.GeminiContent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

interface ChatRepository {
    suspend fun initChat()
    suspend fun getMessages(): Result<Flow<List<Message>>>
    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun saveMessage(message: Message): Result<Unit>
}

class ChatRepositoryImpl(
    private val geminiAPI: GeminiAPI,
    private val userRepository: UserRepository,
    private val chatMessagesAPI: ChatMessagesAPI,
    private val analytics: Analytics,
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
            ?.let { GeminiContent(contents = it) }
            ?.let { geminiContent ->
                val auth = userRepository.getUser().getOrNull()?.id ?: ""
                val generatedContentResponse = geminiAPI.generateContent(auth, geminiContent)
                val content = generatedContentResponse
                    .candidates
                    .firstOrNull()
                    ?.content
                val body = content
                    ?.parts
                    ?.firstOrNull()
                    ?.text
                    ?.let { Json.decodeFromString<Body>(it) }
                if (body != null) {
                    Message(
                        id = geminiContent.contents.size + 1,
                        author = content.role,
                        body = body
                    )
                        .apply { analytics.sendChatResponseEvent(this) }
                        .let { bubbleMessage -> saveMessage(bubbleMessage) }
                        .let { Result.success(Unit) }
                } else {
                    Result.failure(Exception("No messages found"))
                }
            }
            ?: Result.failure(Exception("No messages found"))

    override suspend fun saveMessage(message: Message): Result<Unit> =
        chatMessagesAPI.saveMessage(message)
}