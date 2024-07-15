package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.formattedDuration
import data.remote.Analytics
import data.remote.Body
import data.remote.Challenge
import data.remote.ChatAPI
import data.remote.Message
import data.local.UsageAPI
import data.startOfWeekInMillis
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.SimpleSyntax
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import presentation.model.ChallengeCategory
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UICallToActionType
import presentation.model.UIChallenge
import presentation.model.UIMessage
import presentation.model.UIMessageBody
import presentation.model.UIUsageStats

class BubbleTabScreenModel(
    private val chatAPI: ChatAPI,
    private val analytics: Analytics,
    private val usageAPI: UsageAPI,
) : ScreenModel, ContainerHost<BubbleTabState, BubbleTabSideEffect> {
    override val container: Container<BubbleTabState, BubbleTabSideEffect> =
        screenModelScope.container(BubbleTabState()) {
            loadUsageStats()
        }

    fun loadUsageStats() = intent {
        val hasPermission = usageAPI.hasPermission()
        if (!hasPermission) {
            sendBubbleRequestPermissionMessage()
        } else if (!state.bubblerHasBeenIntroduced) {
            sendBubblerIntroductionMessage()
        }
    }

    private suspend fun SimpleSyntax<BubbleTabState, BubbleTabSideEffect>.sendBubbleRequestPermissionMessage() {
        reduce {
            state.copy(
                messages = state.messages + UIBubbleMessage(
                    id = state.messages.size + 1,
                    author = "model",
                    body = UIMessageBody(
                        message = """
                            ¡Ups! parece que no tengo permiso de revisar tu tiempo en pantalla. Puedes activar esta función en "Acceso al uso" 
                        """.trimIndent(),
                        callToAction = UICallToActionType.REQUEST_USAGE_ACCESS_SETTINGS
                    )
                )
            )
        }
    }

    private suspend fun SimpleSyntax<BubbleTabState, BubbleTabSideEffect>.sendBubblerIntroductionMessage() {
        val usageStats = usageAPI.getUsageStats()
            .filterNot { usageStats ->
                usageAPI.packagesToFilter().any { usageStats.packageName.startsWith(it) }
            }
        reduce {
            state.copy(
                usageStats = usageStats
                    .map { UIUsageStats(it.packageName, it.totalTimeInForeground) }
            )
        }
        val bubblerIntroductionMessage = if (state.usageStats.isNotEmpty()) {
            val averageTimeInForeground = state.usageStats
                .map { it.totalTimeInForeground }
                .average()
                .toLong()
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
        val bubbleMessage = chatAPI.sendMessage(
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
    val usageStats: List<UIUsageStats> = emptyList(),
) {
    val remainingFreeMessages: Int
        get() = messagesLimit - messages
            .count { !it.isFree }
}

sealed class BubbleTabSideEffect