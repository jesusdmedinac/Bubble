package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class DataReward(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val points: Int = 0,
)