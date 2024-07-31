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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.syntax.simple.subIntent
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
    private val chatAIAPI: ChatAIAPI,
    private val analytics: Analytics,
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
                chatAIAPI.initModel()
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
                isFree = true
            )
            reduce {
                state.copy(
                    bubblerHasBeenIntroduced = true
                )
            }
        }
    }

    fun sendMessage(textMessage: String, isFree: Boolean = false) = intent {
        reduce {
            state.copy(
                loading = true
            )
        }
        reduce {
            val updatedMessages = listOf(
                UIBubblerMessage(
                    id = state.messages.size + 1,
                    author = "user",
                    body = UIMessageBody(message = textMessage),
                    isFree = isFree
                )
            ) + state.messages
            state.copy(
                messages = updatedMessages
            )
        }
        val bubbleMessage = chatAIAPI.sendMessage(
            state.messages.map { uiMessage ->
                Message(
                    author = uiMessage.author,
                    body = if (uiMessage.author == "user") {
                        val uiBubblerMessage = uiMessage as UIBubblerMessage
                        Body(
                            message = uiBubblerMessage.body.message,
                            challenge = uiBubblerMessage.body.challenge?.let { challenge ->
                                Challenge(
                                    id = challenge.id,
                                    title = challenge.name,
                                    description = challenge.description,
                                    image = challenge.image,
                                )
                            }
                        )
                    } else {
                        val uiBubbleMessage = uiMessage as UIBubbleMessage
                        Body(
                            message = uiBubbleMessage.body.message,
                            challenge = uiBubbleMessage.body.challenge?.let { challenge ->
                                Challenge(
                                    id = challenge.id,
                                    title = challenge.name,
                                    description = challenge.description,
                                    image = challenge.image
                                )
                            }
                        )
                    }
                )
            }
        )
        analytics.sendChatResponseEvent(bubbleMessage)
        reduce {
            val updatedMessages = listOf(
                UIBubbleMessage(
                    id = state.messages.size + 1,
                    author = bubbleMessage.author,
                    body = bubbleMessage.body.let { body ->
                        UIMessageBody(
                            message = body.message ?: "",
                            challenge = body.challenge?.let { challenge ->
                                UIChallenge(
                                    id = challenge.id,
                                    name = challenge.title,
                                    description = challenge.description,
                                    image = challenge.image,
                                    challengeCategory = ChallengeCategory.TODO
                                )
                            }
                        )
                    }
                )
            ) + state.messages
            state.copy(
                messages = updatedMessages
            )
        }
        reduce {
            state.copy(
                loading = false
            )
        }
    }
}

data class BubbleTabState(
    val loading: Boolean = false,
    val bubblerHasBeenIntroduced: Boolean = false,
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