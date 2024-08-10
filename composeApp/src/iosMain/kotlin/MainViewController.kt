import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import data.local.HasUsagePermissionState
import data.remote.Analytics
import data.local.NetworkAPI
import data.remote.Event
import data.local.SendingData
import data.local.UsageAPI
import data.local.UsageStats
import di.LocalUsageAPI
import di.LocalSendingData
import di.LocalAnalytics
import di.LocalNetworkAPI
import platform.UIKit.UIActivity
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

fun MainViewController(
    networkAPI: NetworkAPI,
) = ComposeUIViewController {
    CompositionLocalProvider(LocalNetworkAPI provides networkAPI) {
        val usageAPIImpl = object : UsageAPI {
            override suspend fun onHasUsagePermissionStateChange(onChange: (HasUsagePermissionState) -> Unit) {
            }

            override fun requestUsagePermission() {
            }

            override fun hasPermission(): Boolean =
                false

            override fun queryUsageStats(beginTime: Long, endTime: Long): List<UsageStats> =
                emptyList()

            override fun packagesToFilter(): List<String> =
                emptyList()

            override fun getUsageEvents(beginTime: Long, endTime: Long): MutableMap<String, Long> =
                mutableMapOf()
        }
        CompositionLocalProvider(LocalUsageAPI provides usageAPIImpl) {
            val sendingData = object : SendingData {
                override fun sendPlainText(data: String) {
                    shareText(data)
                }
            }
            CompositionLocalProvider(LocalSendingData provides sendingData) {
                val analytics = object : Analytics {
                    override fun sendEvent(event: Event) {
                        // TODO implement send event to Firebase
                    }
                }
                CompositionLocalProvider(LocalAnalytics provides analytics) {
                    App()
                }
            }
        }
    }
}

fun shareText(text: String) {
    val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
    shareTextFromViewController(rootViewController, text)
}

fun shareTextFromViewController(viewController: UIViewController?, text: String) {
    val activityItems = listOf(text)
    val applicationActivities: List<UIActivity>? = null
    val activityViewController = UIActivityViewController(activityItems, applicationActivities)

    viewController?.presentViewController(activityViewController, true, null)
}