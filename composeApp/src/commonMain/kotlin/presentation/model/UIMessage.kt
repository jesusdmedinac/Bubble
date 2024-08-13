package presentation.model

import data.remote.model.DataBody
import data.remote.model.DataMessage
import kotlinx.serialization.Serializable

sealed interface UIMessage {
    val id: Int
    val author: String
    val body: UIMessageBody
    val isFree: Boolean

    fun toMessage(): DataMessage = DataMessage(
        id = id,
        author = author,
        dataBody = body.toBody(),
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
    fun toBody(): DataBody = DataBody(
        message = message,
        dataChallenge = challenge?.toChallenge()
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
        .decodeFromString<StructuredBody>(dataBody)
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