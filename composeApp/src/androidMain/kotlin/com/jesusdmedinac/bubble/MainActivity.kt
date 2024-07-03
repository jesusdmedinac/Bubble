package com.jesusdmedinac.bubble

import App
import LocalAnalytics
import LocalBuildConfig
import LocalChatAPI
import LocalSendingData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.jesusdmedinac.bubble.data.ChatAPIImpl
import data.Analytics
import data.BuildConfig
import data.ChatAPI
import data.Event
import data.Message
import data.SendingData
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
                ChatAPICompositionProvider()
            }
        }
    }

    @Composable
    private fun ChatAPICompositionProvider() {
        val chatAPIImpl = ChatAPIImpl(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
        })
        CompositionLocalProvider(LocalChatAPI provides chatAPIImpl) {
            SendingDataCompositionProvider()
        }
    }

    @Composable
    private fun SendingDataCompositionProvider() {
        CompositionLocalProvider(LocalSendingData provides object : SendingData {
            override fun sendPlainText(data: String) {
                sendingPlainText(data)
            }
        }) {
            AnalyticsCompositionProvider()
        }
    }

    @Composable
    private fun AnalyticsCompositionProvider() {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val analytics = object : Analytics {
            override fun sendEvent(event: Event) {
                firebaseAnalytics.logEvent(event.name) {
                    event.params.forEach { (key, value) ->
                        when (value) {
                            is Long -> param(key, value)
                            is Double -> param(key, value)
                            is String -> param(key, value)
                            is Bundle -> param(key, value)
                            is Array<*> -> {
                                @Suppress("UNCHECKED_CAST")
                                param(key, value as Array<Bundle>)
                            }
                        }
                    }
                }
            }
        }
        CompositionLocalProvider(LocalAnalytics provides analytics) {
            KamelCompositionProvider()
        }
    }

    @Composable
    private fun KamelCompositionProvider() {
        val androidConfig = remember {
            KamelConfig {
                takeFrom(KamelConfig.Default)
                httpFetcher(getHttpClient())
                resourcesFetcher(this@MainActivity)
            }
        }
        CompositionLocalProvider(LocalKamelConfig provides androidConfig) {
            BuildConfigCompositionProvider()
        }
    }

    @Composable
    private fun BuildConfigCompositionProvider() {
        val buildConfig = object : BuildConfig {
            override val versionName: String
                get() = com.jesusdmedinac.bubble.BuildConfig.VERSION_NAME
        }
        CompositionLocalProvider(LocalBuildConfig provides buildConfig) {
            App()
        }
    }

    private fun sendingPlainText(data: String) = runCatching {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, data)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}