package data.utils

import data.getDayOfTheWeek
import data.local.UsageStats

fun List<UsageStats>.getAverageDailyUsage(): Long {
    if (isEmpty()) return 0L
    val today = getDayOfTheWeek()

    val totalUsage = sumOf { it.totalTimeInForeground }
    return totalUsage / today
}