package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import data.local.NetworkAPI
import data.local.SendingData
import data.local.UsageAPI
import data.remote.AnalyticsAPI
import org.koin.core.module.Module
import org.koin.dsl.module

val LocalAppNavigator: ProvidableCompositionLocal<Navigator?> =
    staticCompositionLocalOf { null }

val LocalAnalyticsAPI: ProvidableCompositionLocal<AnalyticsAPI> =
    staticCompositionLocalOf { AnalyticsAPI.Default }

val LocalSendingData: ProvidableCompositionLocal<SendingData> =
    staticCompositionLocalOf { SendingData.Default }

val LocalUsageAPI: ProvidableCompositionLocal<UsageAPI> =
    staticCompositionLocalOf { UsageAPI.Default }

val LocalNetworkAPI: ProvidableCompositionLocal<NetworkAPI> =
    staticCompositionLocalOf { NetworkAPI.Default }

@Composable
fun LocalAppProvidablesModule(): Module {
    val analytics = LocalAnalyticsAPI.current
    val sendingData = LocalSendingData.current
    val usageAPI = LocalUsageAPI.current
    val networkAPI = LocalNetworkAPI.current
    return module {
        single<AnalyticsAPI> { analytics }
        single<SendingData> { sendingData }
        single<UsageAPI> { usageAPI }
        single<NetworkAPI> { networkAPI }
    }
}