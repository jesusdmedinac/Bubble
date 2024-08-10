package com.jesusdmedinac.bubble.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android

fun getHttpClient(): HttpClient = HttpClient(Android) {
    engine {
        // this: AndroidEngineConfig
        connectTimeout = 100_000
        socketTimeout = 100_000
    }
}