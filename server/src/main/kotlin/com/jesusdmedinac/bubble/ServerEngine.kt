package com.jesusdmedinac.bubble

import data.remote.FirebaseConfig
import data.remote.GeminiConfig
import di.serverModules
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.config.ApplicationConfig
import kotlinx.serialization.json.Json
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.koin.dsl.module

fun Application.serverEngine() {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Koin) {
        slf4jLogger()
        modules(
            module {
                val geminiKey = environment.config.propertyOrNull("gemini.key")?.getString() ?: ""
                val firebaseWebApiKey = environment.config.propertyOrNull("firebase.web.key")?.getString() ?: ""
                val firebaseDatabaseName = environment.config.propertyOrNull("firebase.database.name")?.getString() ?: ""
                single { FirebaseConfig(firebaseDatabaseName, firebaseWebApiKey) }
                single { GeminiConfig(geminiKey) }
            } + serverModules()
        )
    }
}