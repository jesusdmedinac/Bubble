package data

import kotlinx.serialization.Serializable

@Serializable
data class Message(val author: String, val body: Body)

@Serializable
data class Body(
    val message: String? = "",
    val challenge: Challenge? = null
)