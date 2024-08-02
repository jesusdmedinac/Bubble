package com.jesusdmedinac.bubble.data

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.jesusdmedinac.bubble.BuildConfig
import data.remote.Body
import data.remote.ChatAIAPI
import data.remote.Message
import data.remote.toJsonAsString
import dev.gitlive.firebase.database.DataSnapshot
import dev.gitlive.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class ChatAIAPIImpl(
    private val database: FirebaseDatabase,
    private val json: Json = Json,
) : ChatAIAPI {
    private lateinit var generativeModel: GenerativeModel

    override suspend fun initModel() {
        val systemInstructions = systemInstructions()
        generativeModel = GenerativeModel(
            // The Gemini 1.5 models are versatile and work with most use cases
            modelName = "gemini-1.5-flash",
            // Access your API key as a Build Configuration variable (see "Set up your API key" above)
            apiKey = BuildConfig.apiKey,
            systemInstruction = content { text(systemInstructions) },
        )
    }

    override suspend fun systemInstructions(): String = database
        .reference()
        .child("systemInstructions")
        .valueEvents
        .firstOrNull()
        ?.let { dataSnapshot: DataSnapshot ->
            dataSnapshot.value.toString()
        }
        ?: ""

    override suspend fun sendMessage(messages: List<Message>): Message = runCatching {
        val reversedMessages = messages
            .reversed()
        val history = reversedMessages
            .map { message -> content(role = message.author) { text(message.body.message ?: "") } }
            .dropLast(1)
        val chat = generativeModel.startChat(history = history)
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
        val newAIMessage = Message(
            id = messages.size + 1,
            author = "model",
            body = body
        )
        return newAIMessage
    }
        .fold(
            onSuccess = { it },
            onFailure = {
                Message(
                    id = messages.size + 1,
                    author = "model",
                    body = Body(
                        message = it.message.toString()
                    )
                )
            }
        )
}