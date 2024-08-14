package domain.model

sealed class PointsSubject(
    val points: Int,
    val subject: String,
) {
    data class ChallengeAccepted(
        val challenge: Challenge
    ) : PointsSubject(
        points = POINTS_CHALLENGE_ACCEPTED,
        subject = POINTS_SUBJECT_CHALLENGE_ACCEPTED
    )

    data class MessageSent(
        val messageId: Int
    ) : PointsSubject(
        points = POINTS_MESSAGE_SENT,
        subject = POINTS_SUBJECT_MESSAGE_SENT
    )

    data class ChallengeCompleted(
        val challenge: Challenge
    ) : PointsSubject(
        points = POINTS_CHALLENGE_COMPLETED,
        subject = POINTS_SUBJECT_CHALLENGE_COMPLETED
    )

    data class NewStreak(
        val streakDate: String
    ) : PointsSubject(
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