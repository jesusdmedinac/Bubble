package presentation.model

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class UIDailyUsageStats(
    val packageName: String,
    val date: Long,
    val totalTimeInForeground: Long
) {
    val dateAsLocalDateTime: LocalDateTime
        get() = Instant.fromEpochMilliseconds(date)
            .toLocalDateTime(TimeZone.currentSystemDefault())
}