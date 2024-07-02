import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import data.Analytics
import data.ChatAPI
import data.SendingData
import di.appModules
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.dsl.module
import presentation.screen.MainScreen
import presentation.ui.theme.BubbleTheme

val LocalAppNavigator: ProvidableCompositionLocal<Navigator?> =
    staticCompositionLocalOf { null }

val LocalChatAPI: ProvidableCompositionLocal<ChatAPI> =
    staticCompositionLocalOf { ChatAPI.Default }

val LocalAnalytics: ProvidableCompositionLocal<Analytics> =
    staticCompositionLocalOf { Analytics.Default }

val LocalSendingData: ProvidableCompositionLocal<SendingData> =
    staticCompositionLocalOf { SendingData.Default }

@Composable
@Preview
fun App() {
    val chatAPI = LocalChatAPI.current
    val analytics = LocalAnalytics.current
    val sendingData = LocalSendingData.current
    KoinApplication(application = {
        modules(
            module {
                single<ChatAPI> { chatAPI }
                single<Analytics> { analytics }
                single<SendingData> { sendingData }
            } +
                    appModules()
        )
    }) {
        BubbleTheme {
            Navigator(MainScreen()) { navigator: Navigator ->
                CompositionLocalProvider(
                    LocalAppNavigator provides navigator
                ) {
                    SlideTransition(navigator)
                }
            }
        }
    }
}