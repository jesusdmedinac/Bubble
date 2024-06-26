import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import data.Analytics
import data.ChatAPI
import io.kamel.core.config.KamelConfig
import io.kamel.image.config.Default
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.MainScreen
import presentation.screen.tab.BubbleTab
import presentation.ui.theme.BubbleTheme

val LocalAppNavigator: ProvidableCompositionLocal<Navigator?> =
    staticCompositionLocalOf { null }

val LocalAnalytics: ProvidableCompositionLocal<Analytics> = staticCompositionLocalOf { Analytics.Default }

@Composable
@Preview
fun App(chatAPI: ChatAPI) {
    BubbleTheme {
        Navigator(BubbleTab(chatAPI)) { navigator: Navigator ->
            CompositionLocalProvider(
                LocalAppNavigator provides navigator
            ) {
                SlideTransition(navigator)
            }
        }
    }
}