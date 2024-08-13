package data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeminiContent(
    @SerialName("system_instruction")
    val systemInstructions: ContentItem = ContentItem(),
    val contents: List<ContentItem> = emptyList()
)

@Serializable
data class ContentItem(
    val role: String = "",
    val parts: List<ContentPart> = emptyList(),
)

@Serializable
data class ContentPart(
    val text: String = "",
)