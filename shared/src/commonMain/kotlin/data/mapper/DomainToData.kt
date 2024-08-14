package data.mapper

import data.remote.model.DataBody
import data.remote.model.DataChallenge
import data.remote.model.DataChallengeCategory
import data.remote.model.DataChallengeStatus
import data.remote.model.DataChallenges
import data.remote.model.DataMessage
import data.remote.model.DataMessages
import data.remote.model.DataPointsSubject
import data.remote.model.DataReward
import data.remote.model.DataUser
import domain.model.Body
import domain.model.Challenge
import domain.model.ChallengeCategory
import domain.model.ChallengeStatus
import domain.model.Message
import domain.model.PointsSubject
import domain.model.Reward
import domain.model.User

fun User.toData(): DataUser = DataUser(
    id = id,
    messages = DataMessages(messages.map { it.toData() }),
    challenges = DataChallenges(challenges.map { it.toData() }),
    streak = streak,
    points = points
)

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

fun PointsSubject.toData(): DataPointsSubject = when (this) {
    is PointsSubject.ChallengeAccepted -> DataPointsSubject.ChallengeAccepted(
        challenge = challenge.toData()
    )

    is PointsSubject.ChallengeCompleted -> DataPointsSubject.ChallengeCompleted(
        challenge = challenge.toData()
    )

    is PointsSubject.MessageSent -> DataPointsSubject.MessageSent(
        messageId = messageId
    )

    is PointsSubject.NewStreak -> DataPointsSubject.NewStreak(
        streakDate = streakDate
    )
}