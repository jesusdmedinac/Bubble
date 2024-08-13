package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DataUser(
    val id: String = "",
    val messages: List<DataMessage> = emptyList(),
    val challenges: List<DataChallenge> = emptyList(),
)