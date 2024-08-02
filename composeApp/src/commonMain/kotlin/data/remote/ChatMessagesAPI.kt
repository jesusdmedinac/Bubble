package data.remote

import androidx.compose.runtime.snapshotFlow
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.database.ChildEvent
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.FirebaseDatabase
import dev.gitlive.firebase.database.database
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce

interface ChatMessagesAPI {
    suspend fun systemInstructions(): String
    suspend fun addChatMessage(message: Message)
    suspend fun getChatMessages(): Flow<List<Message>>
}

class ChatMessagesAPIImpl(
    private val auth: FirebaseAuth,
    private val database: FirebaseDatabase,
) : ChatMessagesAPI {
    override suspend fun systemInstructions(): String = database
        .reference()
        .child("systemInstructions")
        .valueEvents
        .firstOrNull()
        ?.value
        .toString()

    override suspend fun addChatMessage(message: Message) {
        auth
            .currentUser
            ?.uid
            ?.let { userId ->
                database
                    .reference()
                    .child(userId)
                    .child("messages")
                    .push()
                    .setValue(message)
            }
    }

    override suspend fun getChatMessages(): Flow<List<Message>> {
        val userId = auth.currentUser?.uid ?: return emptyFlow()
        return database
            .reference()
            .child(userId)
            .child("messages")
            .valueEvents
            .map { snapshot ->
                val messages = mutableListOf<Message>()
                snapshot
                    .children
                    .forEach { childSnapshot ->
                        val message = childSnapshot.value<Message>()
                        messages.add(message)
                    }
                messages.sortedByDescending { it.id }
            }
    }
}