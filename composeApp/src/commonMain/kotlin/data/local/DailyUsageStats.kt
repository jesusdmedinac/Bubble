package data.local

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class DailyUsageStats(
    val usageEvents: MutableMap<String, Long>,
    val date: LocalDate,
)