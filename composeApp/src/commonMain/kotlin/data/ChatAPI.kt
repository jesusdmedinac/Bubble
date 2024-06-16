package data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.Serializable

interface ChatAPI {
    suspend fun sendMessage(messages: List<Message>): Message
}

class ChatAPIImpl(
    private val client: HttpClient
) : ChatAPI {
    override suspend fun sendMessage(messages: List<Message>): Message {
        /**
         * curl \
         *   -H 'Content-Type: application/json' \
         *   -d '{"contents":[{"parts":[{"text":"Explain how AI works"}]}]}' \
         *   -X POST 'https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=YOUR_API_KEY'
         *
         *   response example:
         *   {
         *   "candidates": [
         *     {
         *       "content": {
         *         "parts": [
         *           {
         *             "text": "## Demystifying AI: A Simple Explanation\n\nArtificial intelligence (AI) is essentially making computers think and act like humans. It's not about creating conscious robots, but rather about building machines that can **learn, understand, and make decisions** based on data.\n\nHere's a simplified breakdown:\n\n**1. Data is the Fuel:** AI systems learn from vast amounts of data, like text, images, sounds, or even sensor readings. Think of it as training a child with experiences.\n\n**2. Algorithms are the Brains:** These are sets of instructions that tell the computer how to process and analyze the data. Different algorithms are used for different tasks, like image recognition or language translation.\n\n**3. Machine Learning is the Key:** This is the process where AI systems learn from data without explicit programming. Imagine a computer observing thousands of pictures of cats and learning to recognize them.\n\n**4. Deep Learning is the Powerhouse:** A subset of machine learning, deep learning uses artificial neural networks inspired by the human brain. This allows computers to learn complex patterns from data, enabling tasks like self-driving cars or natural language processing.\n\n**Types of AI:**\n\n* **Narrow or Weak AI:** Designed for specific tasks, like recommending movies or playing chess. Most AI systems today fall into this category.\n* **General or Strong AI:** Aims to create machines with human-level intelligence capable of performing any intellectual task. This is still a distant goal.\n* **Super AI:** Hypothetical AI systems that surpass human intelligence. This remains in the realm of science fiction.\n\n**In essence, AI works by:**\n\n* **Collecting and analyzing vast amounts of data.**\n* **Applying algorithms to learn patterns and relationships.**\n* **Making predictions and decisions based on the learned information.**\n\n**It's important to remember:**\n\n* AI is still evolving, and its capabilities are constantly expanding.\n* AI is a powerful tool that can be used for good or bad. It's crucial to develop ethical guidelines for its development and use.\n* AI is not about replacing humans but rather augmenting our abilities and making our lives easier.\n\nThis is a basic overview of AI. The field is complex and multifaceted, but this explanation provides a foundation for understanding its core concepts.\n"
         *           }
         *         ],
         *         "role": "model"
         *       },
         *       "finishReason": "STOP",
         *       "index": 0,
         *       "safetyRatings": [
         *         {
         *           "category": "HARM_CATEGORY_SEXUALLY_EXPLICIT",
         *           "probability": "NEGLIGIBLE"
         *         },
         *         {
         *           "category": "HARM_CATEGORY_HATE_SPEECH",
         *           "probability": "NEGLIGIBLE"
         *         },
         *         {
         *           "category": "HARM_CATEGORY_HARASSMENT",
         *           "probability": "NEGLIGIBLE"
         *         },
         *         {
         *           "category": "HARM_CATEGORY_DANGEROUS_CONTENT",
         *           "probability": "NEGLIGIBLE"
         *         }
         *       ]
         *     }
         *   ],
         *   "usageMetadata": {
         *     "promptTokenCount": 4,
         *     "candidatesTokenCount": 468,
         *     "totalTokenCount": 472
         *   }
         * }
         */
        val response = client
            .post("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=AIzaSyCqJirMwoz8fcMNbfcYXnge0dBro5DdwLs") {
                contentType(ContentType.Application.Json)
                setBody(
                    RequestBody(
                        systemInstruction = "Tu nombre es Maxister",
                        contents = messages
                            .map { Content(parts = listOf(Part(text = it.body)), role = it.author) },
                    )
                )
            }
        val responseBody = response.body<ResponseBody>()
        return Message(
            "model",
            responseBody.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "No candidates"
        )
    }
}

data class Message(val author: String, val body: String)

@Serializable
data class RequestBody(
    val contents: List<Content> = emptyList(),
    val systemInstruction: String = ""
)

@Serializable
data class Part(
    val text: String = "",
)

@Serializable
data class ResponseBody(
    val candidates: List<Candidate> = emptyList(),
    val usageMetadata: UsageMetadata = UsageMetadata(),
)

@Serializable
data class Candidate(
    val content: Content = Content(),
    val finishReason: String = "",
    val index: Int = 0,
    val safetyRatings: List<SafetyRating> = emptyList(),
)

@Serializable
data class Content(
    val parts: List<Part> = emptyList(),
    val role: String = "",
)

@Serializable
data class SafetyRating(
    val category: String = "",
    val probability: String = "",
)

@Serializable
data class UsageMetadata(
    val promptTokenCount: Int = 0,
    val candidatesTokenCount: Int = 0,
    val totalTokenCount: Int = 0,
)

@Serializable
data class Challenge(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val rewards: List<Reward> = emptyList(),
)

@Serializable
data class Reward(
    val id: Int = -1,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    val points: Int = 0,
)