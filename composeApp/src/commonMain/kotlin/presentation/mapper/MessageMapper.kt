package presentation.mapper

import data.remote.model.DataBody
import data.remote.model.DataMessage
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UIMessage
import presentation.model.UIMessageBody

fun DataBody.toUIMessageBody(): UIMessageBody = UIMessageBody(
    message = message ?: "",
    challenge = dataChallenge?.toUIChallenge(),
    callToAction = null
)

fun DataMessage.toUIMessage(): UIMessage = if (author == "user") {
    UIBubblerMessage(
        id = id,
        author = author,
        body = dataBody.toUIMessageBody()
    )
} else {
    UIBubbleMessage(
        id = id,
        author = author,
        body = dataBody.toUIMessageBody()
    )
}