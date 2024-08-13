package data.remote.model

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
)