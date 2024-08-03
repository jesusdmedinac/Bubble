package domain

import data.remote.Analytics
import data.remote.AuthAPI
import data.remote.Chat
import data.remote.ChatAIAPI
import data.remote.ChatMessagesAPI
import data.remote.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

interface ChatRepository {
    suspend fun initChat()
    suspend fun getMessages(): Result<Flow<List<Message>>>
    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun saveMessage(message: Message): Result<Unit>
}

class ChatRepositoryImpl(
    private val chatAIAPI: ChatAIAPI,
    private val userRepository: UserRepository,
    private val chatMessagesAPI: ChatMessagesAPI,
    private val analytics: Analytics,
) : ChatRepository {
    override suspend fun initChat() {
        userRepository
            .initUser()
            .onSuccess {
                chatAIAPI.initModel()
            }
            .onFailure {
                it.printStackTrace()
            }
    }

    override suspend fun getMessages(): Result<Flow<List<Message>>> =
        chatMessagesAPI.getChatMessages()

    override suspend fun sendMessage(message: Message): Result<Unit> =
        run { chatMessagesAPI.saveMessage(message) }
            .let { getMessages() }
            .fold(
                onSuccess = { it },
                onFailure = { null }
            )
            ?.firstOrNull()
            ?.let { chatAIAPI.sendMessage(it) }
            ?.apply { analytics.sendChatResponseEvent(this) }
            ?.let { bubbleMessage -> chatMessagesAPI.saveMessage(bubbleMessage) }
            ?.let { Result.success(Unit) }
            ?: Result.failure(Exception("No messages found"))

    override suspend fun saveMessage(message: Message): Result<Unit> = chatMessagesAPI.saveMessage(message)
}