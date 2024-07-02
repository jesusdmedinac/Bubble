package presentation.model

import kotlinx.serialization.Serializable

sealed interface UIMessage {
    val id: Int
    val author: String
    val body: UIMessageBody
}

data class UIBubblerMessage(
    override val id: Int,
    override val author: String,
    override val body: UIMessageBody,
) : UIMessage

data class UIBubbleMessage(
    override val id: Int,
    override val author: String,
    override val body: UIMessageBody
) : UIMessage

@Serializable
data class UIMessageBody(
    val message: String,
    val challenge: UIChallenge? = null
)

/*
runCatching {
    Json {
        ignoreUnknownKeys = true
    }
        .decodeFromString<StructuredBody>(body)
}
.fold(
onSuccess = { it },
onFailure = {
    StructuredBody(
        message = it.message ?: "Unknown error",
        challenge = Challenge()
    )
}
)*/