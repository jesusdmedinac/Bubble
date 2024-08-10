package data.remote

import data.remote.model.ContentItem
import data.remote.model.ContentPart
import data.remote.model.GeminiContent
import data.remote.model.GenerateContentResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

interface GeminiAPI {
    suspend fun generateContent(auth: String, geminiContent: GeminiContent): GenerateContentResponse
}

class GeminiAPIImpl(
    private val bubbleInstructionsAPI: BubbleInstructionsAPI,
    private val geminiConfig: GeminiConfig,
    private val httpClient: HttpClient
) : GeminiAPI {
    override suspend fun generateContent(
        auth: String,
        geminiContent: GeminiContent
    ): GenerateContentResponse {
        val systemInstructions = bubbleInstructionsAPI.getBubbleInstructions(auth)
        val geminiContentWithSystemInstructions = geminiContent
            .copy(
                systemInstructions = ContentItem(
                    role = "system",
                    parts = listOf(
                        ContentPart(
                            text = systemInstructions
                        )
                    )
                )
            )
        val response =
            httpClient.post("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${geminiConfig.key}") {
                contentType(ContentType.Application.Json)
                setBody(geminiContentWithSystemInstructions)
            }
        return response.body<GenerateContentResponse>()
    }
}