package data.mapper

import data.remote.model.DataBody
import data.remote.model.DataChallenge
import data.remote.model.DataChallengeCategory
import data.remote.model.DataChallengeStatus
import data.remote.model.DataChallenges
import data.remote.model.DataMessage
import data.remote.model.DataMessages
import data.remote.model.DataReward
import data.remote.model.DataUser
import domain.model.Body
import domain.model.Challenge
import domain.model.ChallengeCategory
import domain.model.ChallengeStatus
import domain.model.Message
import domain.model.Reward
import domain.model.User

fun DataMessage.toDomain(): Message = Message(
    id = id,
    author = author,
    body = body.toDomain()
)

fun DataBody.toDomain(): Body = Body(
    message = message,
    challenge = challenge?.toDomain()
)

fun DataChallenge.toDomain(): Challenge = Challenge(
    id = id,
    title = title,
    description = description,
    image = image,
    rewards = rewards.map { it.toDomain() },
    category = category.toDomain(),
    rejected = rejected,
    status = status.toDomain(),
)

fun DataReward.toDomain(): Reward = Reward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun DataChallengeCategory.toDomain(): ChallengeCategory =
    ChallengeCategory.valueOf(name)

fun DataChallengeStatus.toDomain(): ChallengeStatus =
    ChallengeStatus.valueOf(name)

fun DataUser.toDomain(): User = User(
    id = id,
    messages = messages.toDomain(),
    challenges = challenges.toDomain(),
)

fun DataMessages.toDomain(): List<Message> =
    messages.map { it.toDomain() }

fun DataChallenges.toDomain(): List<Challenge> =
    challenges.map { it.toDomain() }