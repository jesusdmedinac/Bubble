package com.jesusdmedinac.bubble

import App
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import com.jesusdmedinac.bubble.data.ChatAPIImpl
import com.jesusdmedinac.bubble.data.NetworkAPIImpl
import com.jesusdmedinac.bubble.data.UsageAPIImpl
import data.local.BuildConfig
import data.local.SendingData
import data.remote.Analytics
import data.remote.Event
import di.LocalAnalytics
import di.LocalBuildConfig
import di.LocalChatAPI
import di.LocalNetworkAPI
import di.LocalSendingData
import di.LocalUsageAPI
import getHttpClient
import io.kamel.core.config.KamelConfig
import io.kamel.core.config.httpFetcher
import io.kamel.core.config.takeFrom
import io.kamel.image.config.Default
import io.kamel.image.config.LocalKamelConfig
import io.kamel.image.config.resourcesFetcher
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val networkAPIImpl: NetworkAPIImpl by lazy { NetworkAPIImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))

        setupNetworkCallback()

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NetworkAPICompositionProvider()
            }
        }
    }

    private fun setupNetworkCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            // .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR) Not sure if it is required
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                println("Network available")
                networkAPIImpl.isConnected.update { true }
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                println("Network capabilities changed")
                println("dani Network: $network")
                println("dani Network capabilities: $networkCapabilities")
                networkAPIImpl
                    .upstreamBandWidthKbps
                    .update { networkCapabilities.linkUpstreamBandwidthKbps }
                networkAPIImpl
                    .downstreamBandWidthKbps
                    .update { networkCapabilities.linkDownstreamBandwidthKbps }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                println("Network lost")
                networkAPIImpl.isConnected.update { false }
            }
        }

        val connectivityManager =
            getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
        connectivityManager.activeNetworkInfo
    }

    @Composable
    private fun NetworkAPICompositionProvider() {
        CompositionLocalProvider(LocalNetworkAPI provides networkAPIImpl) {
            UsageAPICompositionProvider()
        }
    }

    @Composable
    private fun UsageAPICompositionProvider() {
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