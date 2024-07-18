package data.local

import kotlinx.serialization.Serializable

@Serializable
data class UsageStats(
    val packageName: String,
    val firstTimeStamp: Long,
    val lastTimeStamp: Long,
    val lastTimeUsed: Long,
    val totalTimeInForeground: Long,
    val totalTimeVisible: Long,
)