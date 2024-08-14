package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.formattedDuration
import data.local.UsageAPI
import data.mapper.toData
import data.remote.AnalyticsAPI
import data.startOfWeekInMillis
import domain.repository.ChallengeRepository
import domain.usecase.ChallengeUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
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
import presentation.mapper.toDomain
import presentation.mapper.toUI
import presentation.model.ChallengeStatus
import presentation.model.UIChallenge
import presentation.model.UIDailyUsageStats
import presentation.model.UIUsageStats
import kotlin.math.max

class ProfileTabScreenModel(
    private val usageAPI: UsageAPI,
    private val challengeRepository: ChallengeRepository,
    private val analyticsAPI: AnalyticsAPI,
    private val challengeUseCase: ChallengeUseCase,
) : ScreenModel, ContainerHost<ProfileTabState, ProfileTabSideEffect> {
    override val container: Container<ProfileTabState, ProfileTabSideEffect> =
        screenModelScope.container(ProfileTabState()) {
            loadUsageStats()
            coroutineScope {
                launch {
                    challengeRepository
                        .getChallenges()
                        .onSuccess { challengesFlow ->
                            challengesFlow
                                .collect { challenges ->
                                    reduce {
                                        state.copy(challenges = challenges.map { it.toUI() })
                                    }
                                }
                        }
                }
            }
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
                            usageStats = it.usageEvents
                                .map { stats ->
                                    UIUsageStats(
                                        stats.key,
                                        stats.value,
                                    )
                                },
                            date = it.date,
                        )
                    }
            )
        }
    }

    fun rejectChallenge(challenge: UIChallenge) = intent {
        val dataChallenge = challenge
            .copy(rejected = true)
        challengeRepository.saveChallenge(dataChallenge.toDomain())
        analyticsAPI.sendSaveChallengeEvent(
            AnalyticsAPI.SCREEN_PROFILE_TAB,
            dataChallenge.toDomain().toData(),
        )
    }

    fun undoRejection(challenge: UIChallenge) = intent {
        challengeUseCase
            .suggest(challenge.toDomain())
            .onSuccess { domainChallenge ->
                analyticsAPI.sendSaveChallengeEvent(
                    AnalyticsAPI.SCREEN_PROFILE_TAB,
                    domainChallenge.toData(),
                )
            }
            .onFailure {
                it.printStackTrace()
            }
    }

    fun acceptChallenge(challenge: UIChallenge) = intent {
        challengeUseCase
            .accept(challenge.toDomain())
            .onSuccess { domainChallenge ->
                analyticsAPI.sendSaveChallengeEvent(
                    AnalyticsAPI.SCREEN_PROFILE_TAB,
                    domainChallenge.toData(),
                )
            }
    }

    fun completeChallenge(challenge: UIChallenge) = intent {
        challengeUseCase
            .complete(challenge.toDomain())
            .onSuccess { domainChallenge ->
                analyticsAPI.sendSaveChallengeEvent(
                    AnalyticsAPI.SCREEN_PROFILE_TAB,
                    domainChallenge.toData(),
                )
            }
    }

    fun cancelChallenge(challenge: UIChallenge) = intent {
        challengeUseCase
            .cancel(challenge.toDomain())
            .onSuccess { domainChallenge ->
                analyticsAPI.sendSaveChallengeEvent(
                    AnalyticsAPI.SCREEN_PROFILE_TAB,
                    domainChallenge.toData(),
                )
            }
    }
}

data class ProfileTabState(
    val loading: Boolean = false,
    val hasUsagePermission: Boolean = false,
    val isUserLoggedIn: Boolean = false,
    val usageStats: List<UIUsageStats> = emptyList(),
    val todayDate: LocalDateTime? = Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault()),
    val dailyUsageStats: List<UIDailyUsageStats> = emptyList(),
    val challenges: List<UIChallenge> = emptyList()
) {
    fun averageTimeInForeground(): Long = dailyUsageStats
        .sumOf { it.usageStats.sumOf { it.totalTimeInForeground } } /
            max(dailyUsageStats.size, 1)

    fun formattedAverageTimeInForeground(): String = averageTimeInForeground()
        .formattedDuration(includeSeconds = false)

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

    val acceptedChallenges: List<UIChallenge>
        get() = challenges
            .filterNot { it.rejected }
            .filter { it.status == ChallengeStatus.ACCEPTED }

    val completedChallenges: List<UIChallenge>
        get() = challenges
            .filterNot { it.rejected }
            .filter { it.status == ChallengeStatus.COMPLETED }

    val rejectedChallenges: List<UIChallenge>
        get() = challenges
            .filter { it.rejected }
}

sealed class ProfileTabSideEffect