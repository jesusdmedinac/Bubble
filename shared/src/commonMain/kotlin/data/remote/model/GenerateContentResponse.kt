package data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class GenerateContentResponse(
    val candidates: List<Candidate> = emptyList(),
    val usageMetadata: UsageMetadata = UsageMetadata(),
    val error: GeminiError = GeminiError(),
)

@Serializable
data class Candidate(
    val content: Content = Content(),
    val finishReason: String = "",
    val index: Int = 0,
    val safetyRatings: List<SafetyRating> = emptyList()
)

@Serializable
data class Content(
    val parts: List<Part> = emptyList(),
    val role: String = ""
)

@Serializable
data class Part(
    val text: String = ""
)

@Serializable
data class SafetyRating(
    val category: String = "",
    val probability: String = ""
)

@Serializable
data class UsageMetadata(
    val promptTokenCount: Int = 0,
    val candidatesTokenCount: Int = 0,
    val totalTokenCount: Int = 0
)

@Serializable
data class GeminiError(
    val code: Int = 0,
    val message: String = "",
    val status: String = ""
)