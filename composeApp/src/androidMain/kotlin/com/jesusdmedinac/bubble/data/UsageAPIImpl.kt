package com.jesusdmedinac.bubble.data

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Intent
import android.os.Process
import android.provider.Settings
import data.local.UsageAPI
import data.local.UsageStats

class UsageAPIImpl(
    private val activity: Activity
) : UsageAPI {
    val usageStatsManager: UsageStatsManager?
        get() = if (hasPermission()) {
            activity.getSystemService(Activity.USAGE_STATS_SERVICE) as? UsageStatsManager
        } else null

    override fun requestUsageSettings() {
        activity.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
    }

    override fun hasPermission(): Boolean {
        val appOps = activity.getSystemService(Activity.APP_OPS_SERVICE) as AppOpsManager
        val mode =
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                activity.packageName
            )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun getUsageEvents(
        beginTime: Long,
        endTime: Long
    ): MutableMap<String, Long> {
        val events = mutableListOf<UsageEvents.Event>()
        usageStatsManager
            ?.queryEvents(beginTime, endTime)
            ?.also {
                do {
                    val event = UsageEvents.Event()
                    it.getNextEvent(event)
                    events.add(event)
                } while (it.hasNextEvent())
            }

        val stats = mutableMapOf<String, MutableList<UsageEvents.Event>>()
        events.forEach { event ->
            if (stats.containsKey(event.packageName)) {
                stats[event.packageName] = stats[event.packageName]!!.apply {
                    add(event)
                }
            } else {
                stats[event.packageName] = mutableListOf(event)
            }
        }
        val curatedStats = mutableMapOf<String, Long>()
        stats.forEach { (key, value) ->
            var startTimeStamp = 0L
            var endTimeStamp = 0L
            value.forEach { event ->
                when (event.eventType) {
                    UsageEvents.Event.ACTIVITY_RESUMED -> {
                        startTimeStamp = event.timeStamp
                    }

                    UsageEvents.Event.ACTIVITY_PAUSED -> {
                        endTimeStamp = event.timeStamp
                    }

                    else -> Unit
                }

                if (endTimeStamp != 0L && startTimeStamp != 0L) {
                    val totalTimeInForeground = endTimeStamp - startTimeStamp
                    curatedStats[key] = curatedStats[key]?.let { it + totalTimeInForeground }
                        ?: totalTimeInForeground
                    startTimeStamp = 0L
                    endTimeStamp = 0L
                }
            }
        }
        return curatedStats
    }

    override fun queryUsageStats(
        beginTime: Long,
        endTime: Long
    ): List<UsageStats> {
        return usageStatsManager
            ?.queryAndAggregateUsageStats(
                beginTime,
                endTime
            )
            //?.filter { it.value.totalTimeInForeground > 0 }
            ?.filterNot { it.key == activity.packageName }
            ?.map {
                UsageStats(
                    it.key,
                    it.value.firstTimeStamp,
                    it.value.lastTimeStamp,
                    it.value.lastTimeUsed,
                    it.value.totalTimeInForeground,
                )
            }
            ?.sortedByDescending { it.totalTimeInForeground }
            ?: emptyList()
    }

    override fun packagesToFilter(): List<String> = listOf(
        "android",
        "com.android.",
        "com.google.android.",
        "com.samsung.android.",
        "com.sec.",
        "dev.firebase.",
    )
}