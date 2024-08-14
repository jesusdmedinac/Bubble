package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.formattedDuration
import data.local.ConnectionState
import data.local.HasUsagePermissionState
import data.local.NetworkAPI
import data.local.UsageAPI
import data.mapper.toData
import data.remote.AnalyticsAPI
import domain.repository.ChatRepository
import domain.model.Body
import domain.model.Message
import domain.repository.ChallengeRepository
import domain.usecase.AddSendMessagePointsUseCase
import domain.usecase.ChallengeUseCase
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.subIntent
import presentation.mapper.toDomain
import presentation.mapper.toUI
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UIChallenge
import presentation.model.UIDailyUsageStats
import presentation.model.UIMessage
import presentation.model.UIUsageStats
import kotlin.math.max

class BubbleTabScreenModel(
    private val chatRepository: ChatRepository,
    private val usageAPI: UsageAPI,
    private val networkAPI: NetworkAPI,
    private val analyticsAPI: AnalyticsAPI,
    private val challengeRepository: ChallengeRepository,
    private val ChallengeUseCase: ChallengeUseCase,
    private val addSendMessagePointsUseCase: AddSendMessagePointsUseCase,
) : ScreenModel, ContainerHost<BubbleTabState, BubbleTabSideEffect> {
    private val connectionFlow = channelFlow {
        networkAPI.onConnectionStateChange { trySend(it) }
    }

    private val hasUsagePermissionFlow = channelFlow {
        usageAPI.onHasUsagePermissionStateChange { trySend(it) }
    }

    override val container: Container<BubbleTabState, BubbleTabSideEffect> =
        screenModelScope.container(BubbleTabState()) {
            coroutineScope {
                chatRepository.initChat()
                launch {
                    connectionFlow
                        .collect { connectionState ->
                            reduce {
                                state.copy(connectionState = connectionState)
                            }
                            sendBubblerIntroductionMessage()
                        }
                }
                launch {
                    hasUsagePermissionFlow
                        .collect { hasUsagePermissionState ->
                            reduce {
                                state.copy(hasUsagePermissionState = hasUsagePermissionState)
                            }
                            sendBubblerIntroductionMessage()
                        }
                }
                launch {
                    chatRepository
                        .getMessages()
                        .onSuccess { messagesFlow ->
                            messagesFlow
                                .collect { messages ->
                                    messages
                                        .map { it.toUI() }
                                        .also { reduce { state.copy(messages = it) } }
                                    reduce {
                                        state.copy(
                                            bubblerHasBeenIntroduced = state
                                                .messages
                                                .map { it.body.message }
                                                .any { it.startsWith("Hola Bubble") }
                                        )
                                    }
                                    sendBubblerIntroductionMessage()
                                }
                        }
                }
                launch {
                    challengeRepository
                        .getChallenges()
                        .onSuccess { challengesFlow ->
                            challengesFlow
                                .collect { challenges ->
                                    state
                                        .messages
                                        .filter { it.body.challenge != null }
                                        .forEach { uiMessage ->
                                            val challenge = challenges
                                                .firstOrNull { it.id == uiMessage.body.challenge?.id }
                                                ?.toUI()
                                            if (challenge != null) {
                                                val message = when (uiMessage) {
                                                    is UIBubbleMessage -> uiMessage.copy(
                                                        body = uiMessage.body.copy(
                                                            challenge = challenge
                                                        )
                                                    )

                                                    is UIBubblerMessage -> uiMessage.copy(
                                                        body = uiMessage.body.copy(
                                                            challenge = challenge
                                                        )
                                                    )
                                                }
                                                chatRepository.saveMessage(message.toDomain())
                                            }
                                        }
                                }
                        }
                }
            }
        }

    private suspend fun sendBubblerIntroductionMessage() = subIntent {
        if (!state.bubblerHasBeenIntroduced
            && state.connectionState == ConnectionState.Connected
            && state.hasUsagePermissionState == HasUsagePermissionState.Granted
        ) {
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
            val bubblerIntroductionMessage = if (state.dailyUsageStats.isNotEmpty()) {
                val averageTimeInForeground = state.averageTimeInForeground()
                """
                Hola Bubble, mi tiempo en pantalla es de ${averageTimeInForeground.formattedDuration()}
            """.trimIndent()
            } else {
                """
                Hola Bubble
            """.trimIndent()
            }
            sendMessage(
                bubblerIntroductionMessage,
            )
        }
    }

    fun sendMessage(textMessage: String) = intent {
        reduce {
            state.copy(
                loading = true
            )
        }
        addSendMessagePointsUseCase(
            Message(
                id = state.messages.size + 1,
                author = "user",
                body = Body(message = textMessage)
            )
        )
        reduce {
            state.copy(
                loading = false
            )
        }
    }

    fun addChallenge(challenge: UIChallenge) = intent {
        reduce {
            state.copy(
                addingChallenge = true
            )
        }
        ChallengeUseCase
            .accept(challenge.toDomain())
            .onSuccess { domainChallenge ->
                analyticsAPI.sendSaveChallengeEvent(
                    AnalyticsAPI.SCREEN_BUBBLE_TAB,
                    domainChallenge.toData(),
                )
            }
            .onFailure {
                it.printStackTrace()
            }

        reduce {
            state.copy(
                addingChallenge = false
            )
        }
    }

    fun rejectChallenge(challenge: UIChallenge) = intent {
        val dataChallenge = challenge
            .copy(rejected = !challenge.rejected)
        challengeRepository.saveChallenge(dataChallenge.toDomain())
        analyticsAPI.sendSaveChallengeEvent(
            AnalyticsAPI.SCREEN_BUBBLE_TAB,
            dataChallenge.toDomain().toData(),
        )
    }

    fun goToChallenge(challenge: UIChallenge) = intent {
        postSideEffect(BubbleTabSideEffect.GoToChallenge(challenge))
    }
}

data class BubbleTabState(
    val loading: Boolean = false,
    val addingChallenge: Boolean = false,
    val rejectingChallenge: Boolean = false,
    val bubblerHasBeenIntroduced: Boolean = true,
    val messagesLimit: Int = 10,
    val messages: List<UIMessage> = emptyList(),
    val dailyUsageStats: List<UIDailyUsageStats> = emptyList(),
    val hasUsagePermissionState: HasUsagePermissionState = HasUsagePermissionState.Idle,
    val connectionState: ConnectionState = ConnectionState.Idle,
) {
    fun averageTimeInForeground(): Long = dailyUsageStats
        .sumOf { it.usageStats.sumOf { it.totalTimeInForeground } } /
            max(dailyUsageStats.size, 1)

    val remainingFreeMessages: Int
        get() = messagesLimit - messages
            .count { !it.isFree }

}

sealed class BubbleTabSideEffect {
    data object Idle : BubbleTabSideEffect()
    data class GoToChallenge(val challenge: UIChallenge) : BubbleTabSideEffect()
}