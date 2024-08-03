package data.remote

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

    fun sendClickEvent(stringLength: Long) {
        sendEvent(
            Event(
                EVENT_SEND_CLICK,
                listOf(
                    Param(PARAM_SCREEN_NAME, SCREEN_BUBBLE_TAB),
                    Param(PARAM_STRING_LENGTH, stringLength)
                )
            )
        )
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

    fun sendSaveChallengeEvent(
        screenName: String,
        challenge: Challenge,
    ) {
        sendEvent(
            Event(
                EVENT_SAVE_CHALLENGE,
                listOf(
                    Param(PARAM_SCREEN_NAME, SCREEN_BUBBLE_TAB),
                    Param(PARAM_CHALLENGE, challenge.toString()),
                )
            )
        )
    }

    companion object {
        val Default: Analytics = object : Analytics {
            override fun sendEvent(event: Event) {
                TODO("sendEvent on Analytics is not implemented yet")
            }
        }
        const val SCREEN_BUBBLE_TAB = "bb_bubble_tab"
        const val SCREEN_PROFILE_TAB = "bb_profile_tab"

        const val PARAM_SCREEN_NAME = "bb_screen_name"
        const val PARAM_STRING_LENGTH = "bb_string_length"
        const val PARAM_MESSAGE = "bb_message"
        const val PARAM_CHALLENGE = "bb_challenge"

        const val EVENT_SEND_CLICK = "bb_send_click"
        const val EVENT_CHAT_RESPONSE = "bb_chat_response"
        const val EVENT_SAVE_CHALLENGE = "bb_save_challenge"

    }
}