package data.remote

import data.utils.FirebaseUtils
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface ChatMessagesAPI {
    suspend fun systemInstructions(): Result<String>
    suspend fun saveMessage(message: Message): Result<Unit>
    suspend fun getChatMessages(): Result<Flow<List<Message>>>
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

    override suspend fun saveMessage(message: Message): Result<Unit> = runCatching {
        firebaseUtils
            .getCurrentUserChild()
            ?.child("messages")
            ?.child(message.id.toString())
            ?.setValue(message)
    }

    override suspend fun getChatMessages(): Result<Flow<List<Message>>> = runCatching {
        firebaseUtils
            .getCurrentUserChild()
            ?.child("messages")
            ?.valueEvents
            ?.map { snapshot ->
                val messages = mutableListOf<Message>()
                snapshot
                    .children
                    .forEach { childSnapshot ->
                        val message = childSnapshot.value<Message>()
                        messages.add(message)
                    }
                messages.sortedByDescending { it.id }
            }
            ?: emptyFlow()
    }
}