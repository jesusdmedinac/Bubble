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
    suspend fun getMessages(): Flow<List<Message>>
    suspend fun sendMessage(message: Message): Result<Unit>
}

class ChatRepositoryImpl(
    private val chatAIAPI: ChatAIAPI,
    private val authAPI: AuthAPI,
    private val chatMessagesAPI: ChatMessagesAPI,
    private val analytics: Analytics,
) : ChatRepository {
    override suspend fun initChat() {
        authAPI.initAuth()
        chatAIAPI.initModel()
    }

    override suspend fun getMessages(): Flow<List<Message>> = chatMessagesAPI.getChatMessages()

    override suspend fun sendMessage(message: Message): Result<Unit> =
        run { chatMessagesAPI.addChatMessage(message) }
            .let { getMessages() }
            .firstOrNull()
            ?.let { chatAIAPI.sendMessage(it) }
            ?.apply { analytics.sendChatResponseEvent(this) }
            ?.let { bubbleMessage -> chatMessagesAPI.addChatMessage(bubbleMessage) }
            ?.let { Result.success(Unit) }
            ?: Result.failure(Exception("No messages found"))
}