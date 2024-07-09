package com.jesusdmedinac.bubble

import App
import LocalAnalytics
import LocalBuildConfig
import LocalChatAPI
import LocalSendingData
import LocalUsageAPI
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.jesusdmedinac.bubble.data.ChatAPIImpl
import com.jesusdmedinac.bubble.data.UsageAPIImpl
import data.Analytics
import data.BuildConfig
import data.ChatAPI
import data.Event
import data.Message
import data.SendingData
import data.TimeUtils
import data.UsageAPI
import data.UsageStats
import getHttpClient
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.resourcesFetcher
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.painterResource
import org.orbitmvi.orbit.syntax.simple.reduce

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                UsageAPICompositionProvider()
            }
        }
    }

    @Composable
    private fun MainActivity.UsageAPICompositionProvider() {
        val usageAPIImpl = remember {
            UsageAPIImpl(applicationContext)
        }
        LaunchedEffect(usageAPIImpl) {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                usageAPIImpl.usageStatsManager
                usageAPIImpl.checkForPermission()
                usageAPIImpl.getUsageStats()
            }
        }
        CompositionLocalProvider(LocalUsageAPI provides usageAPIImpl) {
            ChatAPICompositionProvider()
        }
    }

    @Composable
    private fun ChatAPICompositionProvider() {
        val chatAPIImpl = remember {
            ChatAPIImpl(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
        CompositionLocalProvider(LocalChatAPI provides chatAPIImpl) {
            SendingDataCompositionProvider()
        }
    }

    @Composable
    private fun SendingDataCompositionProvider() {
        val sendingData = remember {
            object : SendingData {
                override fun sendPlainText(data: String) {
                    sendingPlainText(data)
                }
            }
        }
        CompositionLocalProvider(LocalSendingData provides sendingData) {
            AnalyticsCompositionProvider()
        }
    }

    @Composable
    private fun AnalyticsCompositionProvider() {
        val firebaseAnalytics = remember {
            FirebaseAnalytics.getInstance(this)
        }
        val analytics = remember {
            object : Analytics {
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
        val buildConfig = remember {
            object : BuildConfig {
                override val versionName: String
                    get() = com.jesusdmedinac.bubble.BuildConfig.VERSION_NAME
            }
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