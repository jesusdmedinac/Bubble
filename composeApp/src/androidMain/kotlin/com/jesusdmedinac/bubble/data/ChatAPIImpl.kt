package com.jesusdmedinac.bubble.data

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesusdmedinac.bubble.BuildConfig
import data.remote.Body
import data.remote.ChatAPI
import data.remote.Message
import data.remote.systemInstructions
import data.remote.toJsonAsString
import kotlinx.serialization.json.Json

class ChatAPIImpl(
    private val json: Json = Json
) : ChatAPI {
    private val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with most use cases
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.apiKey,
        systemInstruction = content { text(systemInstructions()) },
    )

    override suspend fun sendMessage(messages: List<Message>): Message = runCatching {
        val reversedMessages = messages
            .reversed()
        val history = reversedMessages
            .map { message -> content(role = message.author) { text(message.body.message ?: "") } }
            .dropLast(1)
        val chat = generativeModel.startChat(
            history = history
        )
        val response = chat.sendMessage(
            reversedMessages.lastOrNull()?.body?.message ?: "Empty message"
        )
        Log.d("ChatAPI", "Response: ${response.text}")

        val jsonAsString = response.text?.toJsonAsString() ?: """
            {
                "message": "No tengo respuesta",
                "challenge": null
            }
        """.trimIndent()
        val body = runCatching {
            json.decodeFromString<Body>(
                jsonAsString
            )
        }
            .fold(
                onSuccess = { it },
                onFailure = {
                    if (it.message?.contains("Unexpected JSON token") == true) {
                        Body(message = response.text)
                    } else {
                        Body(message = "No tengo respuesta")
                    }
                }
            )
        return Message(
            author = "model",
            body = body
        )
    }
        .fold(
            onSuccess = { it },
            onFailure = {
                Message(
                    author = "model",
                    body = Body(
                        message = it.message.toString()
                    )
                )
            }
        )
}