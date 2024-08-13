package data.remote

import data.remote.model.DataMessage
import data.remote.model.DataMessages
import data.utils.FirebaseUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface ChatMessagesAPI {
    suspend fun systemInstructions(): Result<String>
    suspend fun saveMessage(dataMessage: DataMessage): Result<Unit>
    suspend fun getChatMessages(): Result<Flow<DataMessages>>
}

class ChatMessagesAPIImpl(
    private val firebaseUtils: FirebaseUtils,
) : ChatMessagesAPI {
    override suspend fun systemInstructions(): Result<String> = runCatching {
        firebaseUtils
            .database
            .reference()
            .child("systemInstructions")
            .valueEvents
            .firstOrNull()
            ?.value
            .toString()
    }

    override suspend fun saveMessage(dataMessage: DataMessage): Result<Unit> = runCatching {
        firebaseUtils
            .getCurrentUserChild()
            ?.child("messages")
            ?.child(dataMessage.id.toString())
            ?.setValue(dataMessage)
    }

    override suspend fun getChatMessages(): Result<Flow<DataMessages>> = runCatching {
        firebaseUtils
            .getCurrentUserChild()
            ?.child("messages")
            ?.valueEvents
            ?.map { snapshot ->
                val dataMessages = mutableListOf<DataMessage>()
                snapshot
                    .children
                    .forEach { childSnapshot ->
                        val dataMessage = childSnapshot.value<DataMessage>()
                        dataMessages.add(dataMessage)
                    }
                DataMessages(dataMessages.sortedByDescending { it.id })
            }
            ?: emptyFlow()
    }
}