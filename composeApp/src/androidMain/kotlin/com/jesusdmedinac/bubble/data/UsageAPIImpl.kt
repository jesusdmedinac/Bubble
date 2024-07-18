package com.jesusdmedinac.bubble.data

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Intent
import android.os.Build
import android.os.Process
import android.provider.Settings
import data.startOfWeekInMillis
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

    override fun queryUsageStats(
        beginTime: Long,
        endTime: Long
    ): List<UsageStats> {
        return usageStatsManager
            ?.queryAndAggregateUsageStats(
                beginTime,
                endTime
            )
            ?.filter { it.value.totalTimeInForeground > 0 }
            //?.filter { it.packageName != activity.packageName }
            ?.map {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    UsageStats(
                        it.key,
                        it.value.firstTimeStamp,
                        it.value.lastTimeStamp,
                        it.value.lastTimeUsed,
                        it.value.totalTimeInForeground,
                        it.value.totalTimeVisible
                    )
                } else {
                    UsageStats(
                        it.key,
                        it.value.firstTimeStamp,
                        it.value.lastTimeStamp,
                        it.value.lastTimeUsed,
                        it.value.totalTimeInForeground,
                        it.value.totalTimeInForeground,
                    )
                }
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