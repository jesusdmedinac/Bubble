package presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.Analytics
import data.Body
import data.Challenge
import data.ChatAPI
import data.Message
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

class BubbleTabScreenModel(
    private val chatAPI: ChatAPI,
    private val analytics: Analytics,
) : ScreenModel, ContainerHost<BubbleTabState, BubbleTabSideEffect> {
    override val container: Container<BubbleTabState, BubbleTabSideEffect> =
        screenModelScope.container(BubbleTabState())

    fun sendMessage(textMessage: String) = intent {
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
    }
}

data class BubbleTabState(
    val messagesLimit: Int = 50,
    val messages: List<UIMessage> = emptyList()
) {
    val remainingFreeMessages: Int
        get() = messagesLimit - messages
            .filter { it.author == "user" }
            .size
}

sealed class BubbleTabSideEffect