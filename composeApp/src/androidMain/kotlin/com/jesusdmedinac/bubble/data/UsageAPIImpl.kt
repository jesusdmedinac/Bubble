package com.jesusdmedinac.bubble.data

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Intent
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

    override fun queryUsageStats(): List<UsageStats> {
        val startOfWeekInMillis = startOfWeekInMillis()
        val currentTimeInMillis = System.currentTimeMillis()
        return usageStatsManager
            ?.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                startOfWeekInMillis,
                currentTimeInMillis
            )
            ?.filter { it.totalTimeInForeground > 0 }
            ?.filter { it.packageName != activity.packageName }
            ?.map {
                UsageStats(
                    it.packageName,
                    it.firstTimeStamp,
                    it.lastTimeStamp,
                    it.lastTimeUsed,
                    it.totalTimeInForeground,
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