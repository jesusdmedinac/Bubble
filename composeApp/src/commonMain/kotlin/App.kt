import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.screen.MainScreen
import presentation.ui.theme.BubbleTheme

@Composable
@Preview
fun App() {
    BubbleTheme {
        Navigator(MainScreen())
    }
}