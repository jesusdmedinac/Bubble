package data

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

object TimeUtils {
    fun formatDuration(durationInMilliseconds: Long): String {
        var remainingSeconds = durationInMilliseconds / 1000
        val days = remainingSeconds / 86_400
        remainingSeconds -= days * 86_400
        val hours = remainingSeconds / 3_600
        remainingSeconds -= hours * 3_600
        val minutes = remainingSeconds / 60
        remainingSeconds -= minutes * 60
        val seconds = remainingSeconds
        return (if (days > 0) "$days día${if (days > 1) "s" else ""} "
        else "") + (if (hours > 0) "$hours hora${if (hours > 1) "s" else ""} "
        else "") + (if (minutes > 0) "$minutes minuto${if (minutes > 1) "s" else ""} "
        else "") + (if (seconds > 0) "$seconds segundo${if (seconds > 1) "s" else ""} "
        else "")
    }

    fun getStartOfWeekInMillis(): Long {
        // Get the instant of the first day of the week
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
        val startOfWeek = today.date.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)
        return startOfWeek.toEpochDays() * 86_400_000L
    }

    fun getDayOfTheWeek(): Int {
        val today = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
        return today.dayOfWeek.isoDayNumber
    }

    fun getDayOfTheWeekAsString(): String {
        val daysOfWeek =
            listOf("Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado")
        return daysOfWeek[getDayOfTheWeek() - 1]
    }

    fun getAverageDailyUsage(usageStatsList: List<UsageStats>): Long {
        if (usageStatsList.isEmpty()) return 0L
        val today = getDayOfTheWeek()

        val totalUsage = usageStatsList.sumOf { it.totalTimeInForeground }
        return totalUsage / today
    }
}
