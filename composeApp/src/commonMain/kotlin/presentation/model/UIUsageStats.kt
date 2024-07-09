package presentation.model

data class UIUsageStats(
    val packageName: String,
    val totalTimeInForeground: Long,
)