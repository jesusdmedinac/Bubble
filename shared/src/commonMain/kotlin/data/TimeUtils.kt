package data

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn

fun Long.formattedDuration(
    includeDays: Boolean = true,
    includeHours: Boolean = true,
    includeMinutes: Boolean = true,
    includeSeconds: Boolean = true,
    shortenFormat: Boolean = false,
): String {
    var remainingSeconds = this / 1000
    val days = remainingSeconds / 86_400
    remainingSeconds -= days * 86_400
    val hours = remainingSeconds / 3_600
    remainingSeconds -= hours * 3_600
    val minutes = remainingSeconds / 60
    remainingSeconds -= minutes * 60
    val seconds = remainingSeconds
    return if (shortenFormat) {
        (if (includeDays && days > 0) "${days}d "
        else "") + (if (includeHours && hours > 0) "${hours}h "
        else "") + (if (includeMinutes && minutes > 0) "${minutes}m "
        else "") + (if (includeSeconds && seconds > 0) "${seconds}s "
        else "")
    } else {
        (if (includeDays && days > 0) "$days día${if (days > 1) "s" else ""} "
        else "") + (if (includeHours && hours > 0) "$hours hora${if (hours > 1) "s" else ""} "
        else "") + (if (includeMinutes && minutes > 0) "$minutes minuto${if (minutes > 1) "s" else ""} "
        else "") + (if (includeSeconds && seconds > 0) "$seconds segundo${if (seconds > 1) "s" else ""} "
        else "")
    }
        .let {
            if (it.isNotEmpty() && it.last() == ' ') {
                it.substring(0, it.length - 1)
            } else it
        }
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

