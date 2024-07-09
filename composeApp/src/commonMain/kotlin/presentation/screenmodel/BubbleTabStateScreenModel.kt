package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Analytics
import data.Body
import data.Challenge
import data.ChatAPI
import data.Message
import data.TimeUtils
import data.UsageAPI
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import presentation.model.ChallengeCategory
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
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
            sendMessage(
                """
                Hola Bubble, mi tiempo en pantalla es de ${TimeUtils.formatDuration(state.usageStats.sumOf { it.totalTimeInForeground })}
            """.trimIndent()
            )
        }

    fun sendMessage(textMessage: String) = intent {
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
                    body = UIMessageBody(message = textMessage)
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
    val messagesLimit: Int = 10,
    val messages: List<UIMessage> = emptyList(),
    val usageStats: List<UIUsageStats> = emptyList()
) {
    val remainingFreeMessages: Int
        get() = messagesLimit - messages
            .filter { it.author == "user" }
            .size
}

sealed class BubbleTabSideEffect