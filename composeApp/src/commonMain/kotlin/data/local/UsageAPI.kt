package data.local

import data.startOfWeek
import data.today
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus

enum class HasUsagePermissionState {
    Idle,
    Granted,
    Denied
}

interface UsageAPI {
    suspend fun onHasUsagePermissionStateChange(onChange: (HasUsagePermissionState) -> Unit)

    fun requestUsagePermission()
    fun hasPermission(): Boolean
    fun queryUsageStats(beginTime: Long, endTime: Long): List<UsageStats>
    fun packagesToFilter(): List<String>

    fun getUsageEvents(beginTime: Long, endTime: Long): MutableMap<String, Long>

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
            val usageEvents = getUsageEvents(
                beginTime,
                endTime
            )
            /*.filterNot { usageStats ->
                packagesToFilter().any { usageStats.packageName.startsWith(it) }
            }*/

            usagePerDay.add(
                DailyUsageStats(
                    usageEvents = usageEvents,
                    date = cursorDay,
                )
            )
            cursorDay = cursorDay.plus(1, DateTimeUnit.DAY)
        } while (cursorDay.dayOfYear <= today.dayOfYear)
        return usagePerDay
    }

    companion object {
        val Default = object : UsageAPI {
            override suspend fun onHasUsagePermissionStateChange(onChange: (HasUsagePermissionState) -> Unit) {
                TODO("onHasUsagePermissionStateChange on UsageAPI is not yet implemented")
            }

            override fun requestUsagePermission() {
                TODO("requestUsagePermission on UsageAPI is not yet implemented")
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

            override fun getUsageEvents(beginTime: Long, endTime: Long): MutableMap<String, Long> {
                TODO("getUsageEvents on UsageAPI is not yet implemented")
            }
        }
    }
}
