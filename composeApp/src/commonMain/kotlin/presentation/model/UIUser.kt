package presentation.model

data class UIUser(
    val id: String = "",
    val messages: List<UIMessage> = emptyList(),
    val challenges: List<UIChallenge> = emptyList(),
    val streak: List<String> = emptyList(),
    val points: Int = 0,
)