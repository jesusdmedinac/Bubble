package data.local

import kotlinx.serialization.Serializable

@Serializable
data class UsageStats(
    val packageName: String,
    val totalTimeInForeground: Long,
)