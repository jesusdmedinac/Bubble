package data

import data.local.UsageStats
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

fun Long.formattedDuration(
    includeDays: Boolean = true,
    includeHours: Boolean = true,
    includeMinutes: Boolean = true,
    includeSeconds: Boolean = true
): String {
    var remainingSeconds = this / 1000
    val days = remainingSeconds / 86_400
    remainingSeconds -= days * 86_400
    val hours = remainingSeconds / 3_600
    remainingSeconds -= hours * 3_600
    val minutes = remainingSeconds / 60
    remainingSeconds -= minutes * 60
    val seconds = remainingSeconds
    return (if (includeDays && days > 0) "$days día${if (days > 1) "s" else ""} "
    else "") + (if (includeHours && hours > 0) "$hours hora${if (hours > 1) "s" else ""} "
    else "") + (if (includeMinutes && minutes > 0) "$minutes minuto${if (minutes > 1) "s" else ""} "
    else "") + (if (includeSeconds && seconds > 0) "$seconds segundo${if (seconds > 1) "s" else ""} "
    else "")
}

fun today(): LocalDate = Clock.System.todayIn(TimeZone.currentSystemDefault())

fun startOfWeek(): LocalDate {
    val today = today()
    return today.minus(today.dayOfWeek.ordinal, DateTimeUnit.DAY)
}

fun startOfWeekInMillis(): Long {
    val startOfWeek = startOfWeek()
    val startOfWeekInMillis = startOfWeek
        .atStartOfDayIn(TimeZone.currentSystemDefault())
        .toEpochMilliseconds()
    return startOfWeekInMillis
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

