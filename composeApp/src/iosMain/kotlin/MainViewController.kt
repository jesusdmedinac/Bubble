import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import data.remote.Analytics
import data.local.NetworkAPI
import data.remote.ChatAPI
import data.remote.Event
import data.local.SendingData
import data.local.UsageAPI
import data.local.UsageStats
import di.LocalUsageAPI
import di.LocalChatAPI
import di.LocalSendingData
import di.LocalAnalytics
import di.LocalNetworkAPI
import platform.UIKit.UIActivity
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

fun MainViewController(
    chatAPI: ChatAPI,
    networkAPI: NetworkAPI,
) = ComposeUIViewController {
    CompositionLocalProvider(LocalNetworkAPI provides networkAPI) {
        val usageAPIImpl = object : UsageAPI {
            override fun requestUsageSettings() {}

            override fun hasPermission(): Boolean =
                false

            override fun getUsageStats(): List<UsageStats> =
                emptyList()

            override fun packagesToFilter(): List<String> =
                emptyList()
        }
        CompositionLocalProvider(LocalUsageAPI provides usageAPIImpl) {
            CompositionLocalProvider(LocalChatAPI provides chatAPI) {
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