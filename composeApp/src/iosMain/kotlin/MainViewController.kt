import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import data.Analytics
import data.BuildConfig
import data.ChatAPI
import data.Event
import data.SendingData
import platform.UIKit.UIActivity
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import platform.UIKit.UIViewController

fun MainViewController(chatAPI: ChatAPI) = ComposeUIViewController {
    CompositionLocalProvider(LocalChatAPI provides chatAPI) {
        CompositionLocalProvider(LocalSendingData provides object : SendingData {
            override fun sendPlainText(data: String) {
                shareText(data)
            }
        }) {
            CompositionLocalProvider(LocalAnalytics provides object : Analytics {
                override fun sendEvent(event: Event) {
                    // TODO implement send event to Firebase
                }
            }) {
                CompositionLocalProvider(LocalBuildConfig provides object : BuildConfig {
                    override val versionName: String
                        get() = "0.0.3"
                }) {
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