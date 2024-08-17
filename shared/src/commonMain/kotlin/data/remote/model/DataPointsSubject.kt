package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
sealed class DataPointsSubject(
    val points: Int,
    val subject: String,
) {
    @Serializable
    data class ChallengeAccepted(
        val challenge: DataChallenge
    ) : DataPointsSubject(
        points = POINTS_CHALLENGE_ACCEPTED,
        subject = POINTS_SUBJECT_CHALLENGE_ACCEPTED
    )

    @Serializable
    data class MessageSent(
        val messageId: Int
    ) : DataPointsSubject(
        points = POINTS_MESSAGE_SENT,
        subject = POINTS_SUBJECT_MESSAGE_SENT
    )

    @Serializable
    data class ChallengeCompleted(
        val challenge: DataChallenge
    ) : DataPointsSubject(
        points = POINTS_CHALLENGE_COMPLETED,
        subject = POINTS_SUBJECT_CHALLENGE_COMPLETED
    )

    @Serializable
    data class NewStreak(
        val streakDate: String
    ) : DataPointsSubject(
        points = POINTS_NEW_STREAK,
        subject = POINTS_SUBJECT_NEW_STREAK
    )

    companion object {
        const val POINTS_CHALLENGE_ACCEPTED = 1
        const val POINTS_MESSAGE_SENT = 1
        const val POINTS_CHALLENGE_COMPLETED = 10
        const val POINTS_NEW_STREAK = 1

        const val POINTS_SUBJECT_CHALLENGE_ACCEPTED = "bb_challenge_accepted"
        const val POINTS_SUBJECT_MESSAGE_SENT = "bb_message_sent"
        const val POINTS_SUBJECT_CHALLENGE_COMPLETED = "bb_challenge_completed"
        const val POINTS_SUBJECT_NEW_STREAK = "bb_new_streak"
    }
}