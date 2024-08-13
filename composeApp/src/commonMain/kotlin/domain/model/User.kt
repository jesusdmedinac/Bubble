package domain.model

data class User(
    val id: String = "",
    val messages: List<Message> = emptyList(),
    val challenges: List<Challenge> = emptyList(),
)