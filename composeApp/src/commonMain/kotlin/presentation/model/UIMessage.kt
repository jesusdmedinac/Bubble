package presentation.model

import data.remote.Body
import data.remote.Message
import kotlinx.serialization.Serializable

sealed interface UIMessage {
    val id: Int
    val author: String
    val body: UIMessageBody
    val isFree: Boolean

    fun toMessage(): Message = Message(
        id = id,
        author = author,
        body = body.toBody(),
    )
}

data class UIBubblerMessage(
    override val id: Int,
    override val author: String,
    override val body: UIMessageBody,
    override val isFree: Boolean = false
) : UIMessage

data class UIBubbleMessage(
    override val id: Int,
    override val author: String,
    override val body: UIMessageBody,
    override val isFree: Boolean = true
) : UIMessage

@Serializable
data class UIMessageBody(
    val message: String,
    val challenge: UIChallenge? = null,
    val callToAction: UICallToActionType? = null
) {
    fun toBody(): Body = Body(
        message = message,
        challenge = challenge?.toChallenge()
    )
}

@Serializable
enum class UICallToActionType {
    REQUEST_USAGE_ACCESS_SETTINGS
}

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