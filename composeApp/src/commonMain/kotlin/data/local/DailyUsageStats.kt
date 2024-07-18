package data.local

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DailyUsageStats(
    val usageStats: List<UsageStats>,
    val date: LocalDate,
)