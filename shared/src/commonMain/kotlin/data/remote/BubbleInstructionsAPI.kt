package data.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

interface BubbleInstructionsAPI {
    suspend fun getBubbleInstructions(auth: String): String
}

class BubbleInstructionsAPIImpl(
    private val firebaseConfig: FirebaseConfig,
    private val httpClient: HttpClient
) : BubbleInstructionsAPI {
    override suspend fun getBubbleInstructions(auth: String): String {
        val systemInstructions = httpClient.get("https://${firebaseConfig.databaseName}/systemInstructions.json?auth=${auth}")
                .body<String>()
        return systemInstructions.substring(1, systemInstructions.length - 1)
    }
}