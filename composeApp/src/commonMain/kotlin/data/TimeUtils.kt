package data

import data.local.UsageStats
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

fun Long.formattedDuration(): String {
    var remainingSeconds = this / 1000
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

fun startOfWeekInMillis(): Long {
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

fun List<UsageStats>.getAverageDailyUsage(): Long {
    if (isEmpty()) return 0L
    val today = getDayOfTheWeek()

    val totalUsage = sumOf { it.totalTimeInForeground }
    return totalUsage / today
}

