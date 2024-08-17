package presentation.mapper

import domain.model.Body
import domain.model.Challenge
import domain.model.ChallengeCategory
import domain.model.ChallengeStatus
import domain.model.Message
import domain.model.Reward
import domain.model.User
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UIChallenge
import presentation.model.UIMessage
import presentation.model.UIMessageBody
import presentation.model.UIReward
import presentation.model.UIUser

fun UIUser.toDomain(): User = User(
    id = id,
    messages = messages.map { it.toDomain() },
    challenges = challenges.map { it.toDomain() },
    streak = streak,
    points = points
)

fun UIMessage.toDomain(): Message = Message(
    id = id,
    author = author,
    body = body.toDomain()
)

fun UIMessageBody.toDomain(): Body = Body(
    message = message,
    challenge = challenge?.toDomain(),
)

fun UIChallenge.toDomain(): Challenge = Challenge(
    id = id,
    title = name,
    description = description,
    image = image,
    rewards = rewards.map { it.toDomain() },
    category = category.toDomain(),
    rejected = rejected,
    status = status.toDomain(),
)

fun UIReward.toDomain(): Reward = Reward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun presentation.model.ChallengeCategory.toDomain(): ChallengeCategory =
    ChallengeCategory.valueOf(name)

fun presentation.model.ChallengeStatus.toDomain(): ChallengeStatus =
    ChallengeStatus.valueOf(name)