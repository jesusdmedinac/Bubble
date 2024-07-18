package presentation.model

import kotlinx.datetime.LocalDate

data class UIDailyUsageStats(
    val usageStats: List<UIUsageStats>,
    val date: LocalDate,
)