package data.mapper

import data.remote.model.DataBody
import data.remote.model.DataChallenge
import data.remote.model.DataChallengeCategory
import data.remote.model.DataChallengeStatus
import data.remote.model.DataMessage
import data.remote.model.DataReward
import domain.model.Body
import domain.model.Challenge
import domain.model.ChallengeCategory
import domain.model.ChallengeStatus
import domain.model.Message
import domain.model.Reward

fun Message.toData(): DataMessage = DataMessage(
    id = id,
    author = author,
    body = body.toData()
)

fun Body.toData(): DataBody = DataBody(
    message = message,
    challenge = challenge?.toData()
)

fun Challenge.toData(): DataChallenge = DataChallenge(
    id = id,
    title = title,
    description = description,
    image = image,
    rewards = rewards.map { it.toData() },
    category = category.toData(),
    rejected = rejected,
    status = status.toData(),
)

fun Reward.toData(): DataReward = DataReward(
    id = id,
    title = title,
    description = description,
    image = image,
    points = points,
)

fun ChallengeCategory.toData(): DataChallengeCategory =
    DataChallengeCategory.valueOf(name)

fun ChallengeStatus.toData(): DataChallengeStatus =
    DataChallengeStatus.valueOf(name)