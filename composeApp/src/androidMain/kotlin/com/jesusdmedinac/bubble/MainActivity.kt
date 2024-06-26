package com.jesusdmedinac.bubble

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.jesusdmedinac.bubble.data.ChatAPIImpl
import data.ChatAPI
import data.Message
import getHttpClient
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.resourcesFetcher
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
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