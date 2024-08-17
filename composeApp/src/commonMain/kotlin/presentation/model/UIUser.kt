package presentation.model

import data.formatNumber

data class UIUser(
    val id: String = "",
    val messages: List<UIMessage> = emptyList(),
    val challenges: List<UIChallenge> = emptyList(),
    val streak: List<String> = emptyList(),
    val points: Int = 0,
) {
    val formattedPoints: String
        get() = points.toString().formatNumber()
    val formattedStreak: String
        get() = streak.size.toString().formatNumber()
}