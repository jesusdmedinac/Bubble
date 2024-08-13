package com.jesusdmedinac.bubble.presentation.routing

import data.remote.httpClient
import data.remote.model.ContentItem
import data.remote.model.ContentPart
import data.remote.model.GeminiContent
import data.remote.model.GenerateContentResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing

fun Application.routing() {
    routing {
        get("/") {
            call.respondText("Hello, Bubbler!")
        }
        generateContent()
    }
}