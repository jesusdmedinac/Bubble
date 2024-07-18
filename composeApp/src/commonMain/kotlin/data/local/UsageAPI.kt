package data.local

import data.startOfWeek
import data.startOfWeekInMillis
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

interface UsageAPI {
    fun requestUsageSettings()
    fun hasPermission(): Boolean
    fun queryUsageStats(): List<UsageStats>
    fun packagesToFilter(): List<String>

    fun getDailyUsageStatsForWeek(): List<DailyUsageStats> {
        val usageStatsList = queryUsageStats()
            .filterNot { usageStats ->
                packagesToFilter().any { usageStats.packageName.startsWith(it) }
            }

        return usageStatsList.filter { it.totalTimeInForeground > 0 }
            .flatMap { usageStat ->
                val usagePerDay = mutableListOf<DailyUsageStats>()
                val firstTimeStampLocalDateTime = startOfWeek()
                val dayStartLocalDate = firstTimeStampLocalDateTime
                var dayStartInMillis = dayStartLocalDate
                    .atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds()
                val dayEndLocalDate = firstTimeStampLocalDateTime.plus(1, DateTimeUnit.DAY)
                var dayEndInMillis = dayEndLocalDate
                    .atStartOfDayIn(TimeZone.currentSystemDefault())
                    .toEpochMilliseconds()

                var nextDayCounter = 1
                while (dayStartInMillis < usageStat.lastTimeStamp) {
                    usagePerDay.add(
                        DailyUsageStats(
                            usageStat.packageName,
                            dayStartInMillis,
                            usageStat.totalTimeInForeground
                        )
                    )
                    // Calculate next day
                    dayStartInMillis = dayStartLocalDate.plus(nextDayCounter, DateTimeUnit.DAY)
                        .atStartOfDayIn(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                    dayEndInMillis = dayEndLocalDate.plus(++nextDayCounter, DateTimeUnit.DAY)
                        .atStartOfDayIn(TimeZone.currentSystemDefault())
                        .toEpochMilliseconds()
                }

                usagePerDay
            }
            .sortedBy { it.date }
    }

    companion object {
        val Default = object : UsageAPI {
            override fun requestUsageSettings() {
                TODO("requestUsageSettings on UsageAPI is not yet implemented")
            }

            override fun hasPermission(): Boolean {
                TODO("hasPermission on UsageAPI is not yet implemented")
            }

            override fun queryUsageStats(): List<UsageStats> {
                TODO("queryUsageStats on UsageAPI is not yet implemented")
            }

            override fun packagesToFilter(): List<String> {
                TODO("packagesToFilter on UsageAPI is not yet implemented")
            }
        }
    }
}
