package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import data.local.BuildConfig
import data.local.NetworkAPI
import data.local.SendingData
import data.local.UsageAPI
import data.remote.Analytics
import data.remote.ChatAPI
import org.koin.core.module.Module
import org.koin.dsl.module

val LocalAppNavigator: ProvidableCompositionLocal<Navigator?> =
    staticCompositionLocalOf { null }

val LocalChatAPI: ProvidableCompositionLocal<ChatAPI> =
    staticCompositionLocalOf { ChatAPI.Default }

val LocalAnalytics: ProvidableCompositionLocal<Analytics> =
    staticCompositionLocalOf { Analytics.Default }

val LocalSendingData: ProvidableCompositionLocal<SendingData> =
    staticCompositionLocalOf { SendingData.Default }

val LocalBuildConfig: ProvidableCompositionLocal<BuildConfig> =
    staticCompositionLocalOf { BuildConfig.Default }

val LocalUsageAPI: ProvidableCompositionLocal<UsageAPI> =
    staticCompositionLocalOf { UsageAPI.Default }

val LocalNetworkAPI: ProvidableCompositionLocal<NetworkAPI> =
    staticCompositionLocalOf { NetworkAPI.Default }

@Composable
fun LocalAppProvidablesModule(): Module {
    val chatAPI = LocalChatAPI.current
    val analytics = LocalAnalytics.current
    val sendingData = LocalSendingData.current
    val buildConfig = LocalBuildConfig.current
    val usageAPI = LocalUsageAPI.current
    val networkAPI = LocalNetworkAPI.current
    return module {
        single<ChatAPI> { chatAPI }
        single<Analytics> { analytics }
        single<SendingData> { sendingData }
        single<BuildConfig> { buildConfig }
        single<UsageAPI> { usageAPI }
        single<NetworkAPI> { networkAPI }
    }
}