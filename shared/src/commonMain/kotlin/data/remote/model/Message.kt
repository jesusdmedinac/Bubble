package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Chat(
    val messages: List<Message>,
)

@Serializable
data class Message(
    val id: Int,
    val author: String,
    val body: Body,
)

@Serializable
data class Body(
    val message: String? = "",
    val challenge: Challenge? = null
)