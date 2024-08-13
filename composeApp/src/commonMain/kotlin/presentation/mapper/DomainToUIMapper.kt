package presentation.mapper

import domain.model.Body
import domain.model.Challenge
import domain.model.ChallengeCategory
import domain.model.ChallengeStatus
import domain.model.Message
import domain.model.Reward
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UIChallenge
import presentation.model.UIMessage
import presentation.model.UIMessageBody
import presentation.model.UIReward

fun Message.toUI(): UIMessage = if (author == "user") {
    UIBubblerMessage(
        id = id,
        author = author,
        body = body.toUI()
    )
} else {
    UIBubbleMessage(
        id = id,
        author = author,
        body = body.toUI()
    )
}

fun Body.toUI(): UIMessageBody = UIMessageBody(
    message = message ?: "",
    challenge = challenge?.toUI(),
    callToAction = null
)

fun Challenge.toUI(): UIChallenge = UIChallenge(
    id = id,
    name = title,
    description = description,
    image = image,
    rewards = rewards.map { it.toUI() },
    category = category.toUI(),
    rejected = rejected,
    status = status.toUI(),
)

fun Reward.toUI(): UIReward = UIReward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun ChallengeCategory.toUI(): presentation.model.ChallengeCategory =
    presentation.model.ChallengeCategory.valueOf(name)

fun ChallengeStatus.toUI(): presentation.model.ChallengeStatus =
    presentation.model.ChallengeStatus.valueOf(name)