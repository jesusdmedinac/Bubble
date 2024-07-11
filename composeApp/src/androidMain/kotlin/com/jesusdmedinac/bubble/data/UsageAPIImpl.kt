package com.jesusdmedinac.bubble.data

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Process
import data.TimeUtils
import data.local.UsageAPI
import data.local.UsageStats

class UsageAPIImpl(
    private val applicationContext: Context
) : UsageAPI {
    val usageStatsManager: UsageStatsManager?
        get() = if (checkForPermission()) {
            applicationContext.getSystemService(Activity.USAGE_STATS_SERVICE) as? UsageStatsManager
        } else null

    fun checkForPermission(): Boolean {
        val appOps = applicationContext.getSystemService(Activity.APP_OPS_SERVICE) as AppOpsManager
        val mode =
            appOps.checkOpNoThrow(
                AppOpsManager.OPSTR_GET_USAGE_STATS,
                Process.myUid(),
                applicationContext.packageName
            )
        return mode == AppOpsManager.MODE_ALLOWED
    }

    override fun getUsageStats(): List<UsageStats> {
        val startOfWeekInMillis = TimeUtils.getStartOfWeekInMillis()
        val currentTimeInMillis = System.currentTimeMillis()
        return usageStatsManager
            ?.queryUsageStats(
                UsageStatsManager.INTERVAL_WEEKLY,
                startOfWeekInMillis,
                currentTimeInMillis
            )
            ?.filter { it.totalTimeInForeground > 0 }
            ?.filter { it.packageName != applicationContext.packageName }
            ?.map { UsageStats(it.packageName, it.totalTimeInForeground) }
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