package com.jesusdmedinac.bubble.presentation.routing

import data.remote.GeminiAPI
import data.remote.model.GeminiContent
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import org.koin.ktor.ext.inject

fun Routing.generateContent() {
    val geminiAPI by inject<GeminiAPI>()
    post("/generateContent") {
        val auth = call.request.queryParameters["auth"] ?: ""
        val geminiContent = call.receive<GeminiContent>()
        val generateContentResponse = geminiAPI.generateContent(auth, geminiContent)
        call.respond(generateContentResponse)
    }
}