import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import data.ChatAPI
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.LocalAppNavigator
import presentation.screen.MainScreen
import presentation.ui.theme.BubbleTheme

@Composable
@Preview
fun App(chatAPI: ChatAPI) {
    BubbleTheme {
        Navigator(MainScreen(chatAPI)) { navigator: Navigator ->
            CompositionLocalProvider(
                LocalAppNavigator provides navigator
            ) {
                SlideTransition(navigator)
            }
        }
    }
}