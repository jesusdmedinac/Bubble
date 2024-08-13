package domain.model

data class Message(
    val id: Int,
    val author: String,
    val body: Body,
)

data class Body(
    val message: String? = "",
    val challenge: Challenge? = null
)