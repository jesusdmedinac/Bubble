package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.formattedDuration
import data.local.ConnectionState
import data.local.HasUsagePermissionState
import data.local.NetworkAPI
import data.remote.Analytics
import data.remote.Body
import data.remote.Challenge
import data.remote.ChatAIAPI
import data.remote.Message
import data.local.UsageAPI
import domain.ChatRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.subIntent
import presentation.mapper.toUIMessage
import presentation.model.ChallengeCategory
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UIChallenge
import presentation.model.UIDailyUsageStats
import presentation.model.UIMessage
import presentation.model.UIMessageBody
import presentation.model.UIUsageStats
import kotlin.math.max

class BubbleTabScreenModel(
    private val chatRepository: ChatRepository,
    private val usageAPI: UsageAPI,
    private val networkAPI: NetworkAPI,
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
                        .collect { messages ->
                            messages
                                .map { it.toUIMessage() }
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
        chatRepository.sendMessage(
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
}

data class BubbleTabState(
    val loading: Boolean = false,
    val bubblerHasBeenIntroduced: Boolean = true,
    val messagesLimit: Int = 10,
    val messages: List<UIMessage> = emptyList(),
    val dailyUsageStats: List<UIDailyUsageStats> = emptyList(),
    val hasUsagePermissionState: HasUsagePermissionState = data.local.HasUsagePermissionState.Idle,
    val connectionState: ConnectionState = ConnectionState.Idle,
) {
    fun averageTimeInForeground(): Long = dailyUsageStats
        .sumOf { it.usageStats.sumOf { it.totalTimeInForeground } } /
            max(dailyUsageStats.size, 1)

    val remainingFreeMessages: Int
        get() = messagesLimit - messages
            .count { !it.isFree }
}

sealed class BubbleTabSideEffect