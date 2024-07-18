package data.local

import kotlinx.serialization.Serializable

@Serializable
data class DailyUsageStats(val packageName: String, val date: Long, val totalTimeInForeground: Long)