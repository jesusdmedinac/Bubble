import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import data.remote.Analytics
import data.local.BuildConfig
import data.remote.ChatAPI
import data.remote.Event
import data.local.SendingData
import data.local.UsageAPI
import data.local.UsageStats
import platform.UIKit.UIActivity
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

fun MainViewController(chatAPI: ChatAPI) = ComposeUIViewController {
    val usageAPIImpl = object : UsageAPI {
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
                    val buildConfig = object : BuildConfig {
                        override val versionName: String
                            get() = "0.0.3"
                    }
                    CompositionLocalProvider(LocalBuildConfig provides buildConfig) {
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