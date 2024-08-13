package domain.model

data class User(
    val id: String = "",
    val dataMessages: List<Message> = emptyList(),
    val dataChallenges: List<Challenge> = emptyList(),
)