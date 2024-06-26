package data

data class Param<T : Any>(
    val key: String,
    val value: T
)

data class Event(
    val name: String,
    val params: List<Param<Any>>
)

interface Analytics {
    fun sendEvent(event: Event)

    fun sendClickEvent(stringLength: Int) {
        sendEvent(Event(
            EVENT_SEND_CLICK,
            listOf(
                Param(PARAM_SCREEN_NAME, SCREEN_BUBBLE_TAB),
                Param(PARAM_STRING_LENGTH, stringLength)
            )
        ))
    }

    fun sendChatResponseEvent(message: Message) {
        sendEvent(
            Event(
                EVENT_CHAT_RESPONSE,
                listOf(
                    Param(PARAM_SCREEN_NAME, SCREEN_BUBBLE_TAB),
                    Param(PARAM_MESSAGE, message.toString()),
                )
            )
        )
    }

    companion object {
        val Default: Analytics = object : Analytics {
            override fun sendEvent(event: Event) {
                TODO("Not yet implemented")
            }
        }
        const val SCREEN_BUBBLE_TAB = "BubbleTab"

        const val PARAM_SCREEN_NAME = "screen_name"
        const val PARAM_STRING_LENGTH = "string_length"
        const val PARAM_MESSAGE = "message"

        const val EVENT_SEND_CLICK = "send_click"
        const val EVENT_CHAT_RESPONSE = "chat_response"

    }
}