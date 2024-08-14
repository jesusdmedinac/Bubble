package data.remote.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DataMessages(
    val messages: List<DataMessage> = emptyList()
)

@Serializable
data class DataChallenges(
    val challenges: List<DataChallenge> = emptyList()
)

@Serializable
data class DataUser(
    val id: String = "",
    val messages: DataMessages = DataMessages(),
    val challenges: DataChallenges = DataChallenges(),
    val streak: List<String> = emptyList(),
    val points: Int = 0,
    val pointsSubjects: List<DataPointsSubject> = emptyList(),
)