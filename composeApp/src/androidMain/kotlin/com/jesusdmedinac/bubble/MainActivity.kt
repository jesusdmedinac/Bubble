package com.jesusdmedinac.bubble

import App
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import com.jesusdmedinac.bubble.data.ChatAPIImpl
import data.Challenge
import data.ChatAPI
import data.Message
import data.Reward
import getHttpClient
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.resourcesFetcher
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val androidConfig = remember {
                KamelConfig {
                    takeFrom(KamelConfig.Default)
                    httpFetcher(getHttpClient())
                    resourcesFetcher(this@MainActivity)
                }
            }
            CompositionLocalProvider(LocalKamelConfig provides androidConfig) {
                App(
                    chatAPI = ChatAPIImpl(Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                    })
                )
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(chatAPI = object : ChatAPI {
        override suspend fun sendMessage(messages: List<Message>): Message {
            TODO("Not yet implemented")
        }
    })
}