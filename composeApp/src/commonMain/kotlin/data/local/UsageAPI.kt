package data.local

import data.startOfWeek
import data.startOfWeekInMillis
import data.today
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.atTime
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.days

interface UsageAPI {
    fun requestUsageSettings()
    fun hasPermission(): Boolean
    fun queryUsageStats(beginTime: Long, endTime: Long): List<UsageStats>
    fun packagesToFilter(): List<String>

    fun getDailyUsageStatsForWeek(): List<DailyUsageStats> {
        val startOfWeek = startOfWeek()
        val today = today()
        val usagePerDay = mutableListOf<DailyUsageStats>()

        var cursorDay = startOfWeek
        do {
            val beginTime = cursorDay
                .atStartOfDayIn(TimeZone.currentSystemDefault())
                .toEpochMilliseconds()
            val endTime = beginTime + (24 * 60 * 60 * 1000L)
            val usageStats = queryUsageStats(
                beginTime = beginTime,
                endTime = endTime
            )
                /*.filterNot { usageStats ->
                    packagesToFilter().any { usageStats.packageName.startsWith(it) }
                }*/

            usagePerDay.add(
                DailyUsageStats(
                    usageStats = usageStats
                        .filter {
                            it.lastTimeUsed in (beginTime + 1)..<endTime
                        },
                    date = cursorDay,
                )
            )
            cursorDay = cursorDay.plus(1, DateTimeUnit.DAY)
        } while (cursorDay.dayOfYear <= today.dayOfYear)
        return usagePerDay
    }

    companion object {
        val Default = object : UsageAPI {
            override fun requestUsageSettings() {
                TODO("requestUsageSettings on UsageAPI is not yet implemented")
            }

            override fun hasPermission(): Boolean {
                TODO("hasPermission on UsageAPI is not yet implemented")
            }

            override fun queryUsageStats(beginTime: Long, endTime: Long): List<UsageStats> {
                TODO("queryUsageStats on UsageAPI is not yet implemented")
            }

            override fun packagesToFilter(): List<String> {
                TODO("packagesToFilter on UsageAPI is not yet implemented")
            }
        }
    }
}
