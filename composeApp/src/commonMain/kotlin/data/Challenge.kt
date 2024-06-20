package data

import kotlinx.serialization.Serializable

@Serializable
data class Challenge(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val rewards: List<Reward> = emptyList(),
)