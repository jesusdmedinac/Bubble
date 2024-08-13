package data.remote

import data.remote.model.DataChallenge
import data.remote.model.DataMessage
import data.remote.model.DataUser

data class Param<T : Any>(
    val key: String,
    val value: T
)

data class Event(
    val name: String,
    val params: List<Param<Any>>
)

interface AnalyticsAPI {
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

    fun sendChatResponseEvent(dataMessage: DataMessage) {
        sendEvent(
            Event(
                EVENT_CHAT_RESPONSE,
                listOf(
                    Param(PARAM_SCREEN_NAME, SCREEN_BUBBLE_TAB),
                    Param(PARAM_MESSAGE, dataMessage.toString()),
                )
            )
        )
    }

    fun sendSaveChallengeEvent(
        screenName: String,
        dataChallenge: DataChallenge,
    ) {
        sendEvent(
            Event(
                EVENT_SAVE_CHALLENGE,
                listOf(
                    Param(PARAM_SCREEN_NAME, SCREEN_BUBBLE_TAB),
                    Param(PARAM_CHALLENGE, dataChallenge.toString()),
                )
            )
        )
    }

    fun sendUpdateStreakEvent(
        user: DataUser,
        streak: MutableList<String>
    ) {
        sendEvent(
            Event(
                EVENT_UPDATE_STREAK,
                listOf(
                    Param(PARAM_SCREEN_NAME, SCREEN_BUBBLE_TAB),
                    Param(PARAM_USER, user.toString()),
                    Param(PARAM_STREAK, streak.toString()),
                )
            )
        )
    }

    companion object {
        val Default: AnalyticsAPI = object : AnalyticsAPI {
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
        const val PARAM_USER = "bb_user"
        const val PARAM_STREAK = "bb_streak"

        const val EVENT_SEND_CLICK = "bb_send_click"
        const val EVENT_CHAT_RESPONSE = "bb_chat_response"
        const val EVENT_SAVE_CHALLENGE = "bb_save_challenge"
        const val EVENT_UPDATE_STREAK = "bb_update_streak"
    }
}