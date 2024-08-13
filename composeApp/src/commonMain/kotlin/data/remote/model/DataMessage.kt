package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DataMessage(
    val id: Int,
    val author: String,
    val dataBody: DataBody,
)

@Serializable
data class DataBody(
    val message: String? = "",
    val dataChallenge: DataChallenge? = null
)