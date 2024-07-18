package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.formattedDuration
import data.local.DailyUsageStats
import data.local.UsageAPI
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
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
        val usageStats = usageAPI.queryUsageStats()
            .filterNot { usageStats ->
                usageAPI.packagesToFilter().any { usageStats.packageName.startsWith(it) }
            }
        reduce {
            state.copy(
                usageStats = usageStats
                    .map { UIUsageStats(it.packageName, it.totalTimeInForeground) }
            )
        }
        val dailyUsageStats = usageAPI.getDailyUsageStatsForWeek()
        reduce {
            state.copy(
                dailyUsageStats = dailyUsageStats
                    .map {
                        UIDailyUsageStats(
                            it.packageName,
                            it.date,
                            it.totalTimeInForeground
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