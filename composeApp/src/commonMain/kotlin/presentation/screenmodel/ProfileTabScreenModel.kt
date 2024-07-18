package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.formattedDuration
import data.local.DailyUsageStats
import data.local.UsageAPI
import data.startOfWeekInMillis
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.toLocalDateTime
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import presentation.model.UIChallenge
import presentation.model.UIDailyUsageStats
import presentation.model.UIUsageStats

class ProfileTabScreenModel(
    private val usageAPI: UsageAPI,
) : ScreenModel, ContainerHost<ProfileTabState, ProfileTabSideEffect> {
    override val container: Container<ProfileTabState, ProfileTabSideEffect> =
        screenModelScope.container(ProfileTabState()) {
            loadUsageStats()
        }

    fun loadUsageStats() = intent {
        val hasPermission = usageAPI.hasPermission()
        reduce {
            state.copy(
                hasUsagePermission = hasPermission,
            )
        }
        val usageStats = usageAPI.queryUsageStats(
            beginTime = startOfWeekInMillis(),
            endTime = Clock.System.now().toEpochMilliseconds()
        )
            .filterNot { usageStats ->
                usageAPI.packagesToFilter().any { usageStats.packageName.startsWith(it) }
            }
        reduce {
            state.copy(
                usageStats = usageStats
                    .map {
                        UIUsageStats(
                            it.packageName,
                            it.totalTimeInForeground,
                        )
                    }
            )
        }
        val dailyUsageStats = usageAPI.getDailyUsageStatsForWeek()
        reduce {
            state.copy(
                dailyUsageStats = dailyUsageStats
                    .map {
                        UIDailyUsageStats(
                            usageStats = it.usageStats
                                .map { stats ->
                                    UIUsageStats(
                                        stats.packageName,
                                        stats.totalTimeInForeground,
                                    )
                                },
                            date = it.date,
                        )
                    }
            )
        }
    }
}

data class ProfileTabState(
    val loading: Boolean = false,
    val hasUsagePermission: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val usageStats: List<UIUsageStats> = emptyList(),
    val savedChallenges: List<UIChallenge> = emptyList(),
    val completedChallenges: List<UIChallenge> = emptyList(),
    val todayDate: LocalDateTime? = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val dailyUsageStats: List<UIDailyUsageStats> = emptyList(),
) {
    fun formattedTotalTimeInForeground(): String = usageStats
        .sumOf { it.totalTimeInForeground }
        .formattedDuration(
            includeSeconds = false
        )

    fun formattedTodayDate(): String = todayDate?.let {
        LocalDateTime.Format {
            chars("Hoy, ")
            val dayOfWeekList = listOf(
                "Lun",
                "Mar",
                "Mie",
                "Jue",
                "Vie",
                "Sab",
                "Dom"
            )
            dayOfWeek(DayOfWeekNames(dayOfWeekList))
            chars(" ")
            dayOfMonth()
            chars(" de ")
            val monthListInSpanish = listOf(
                "Enero",
                "Febrero",
                "Marzo",
                "Abril",
                "Mayo",
                "Junio",
                "Julio",
                "Agosto",
                "Septiembre",
                "Octubre",
                "Noviembre",
                "Diciembre"
            )
            monthName(MonthNames(monthListInSpanish))
        }
            .format(it)
    }
        ?: ""
}

sealed class ProfileTabSideEffect