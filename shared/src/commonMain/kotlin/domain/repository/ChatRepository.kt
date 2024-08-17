package domain.repository

import domain.model.Message
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun initChat()
    suspend fun getMessages(): Result<Flow<List<Message>>>
    suspend fun sendMessage(message: Message): Result<Unit>
    suspend fun saveMessage(message: Message): Result<Unit>
}