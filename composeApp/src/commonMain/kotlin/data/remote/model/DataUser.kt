package data.remote.model

data class DataUser(
    val id: String = "",
    val dataMessages: List<DataMessage> = emptyList(),
    val dataChallenges: List<DataChallenge> = emptyList(),
)