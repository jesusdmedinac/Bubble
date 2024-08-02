package presentation.mapper

import data.remote.Body
import data.remote.Challenge
import data.remote.Message
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UIChallenge
import presentation.model.UIMessage
import presentation.model.UIMessageBody

private fun Challenge.toUIChallenge(): UIChallenge = UIChallenge(
    id = id,
    name = title,
    description = description,
    image = image,
)

fun Body.toUIMessageBody(): UIMessageBody = UIMessageBody(
    message = message ?: "",
    challenge = challenge?.toUIChallenge(),
    callToAction = null
)

fun Message.toUIMessage(): UIMessage = if (author == "user") {
    UIBubblerMessage(
        id = id,
        author = author,
        body = body.toUIMessageBody()
    )
} else {
    UIBubbleMessage(
        id = id,
        author = author,
        body = body.toUIMessageBody()
    )
}