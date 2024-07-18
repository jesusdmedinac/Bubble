import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.KoinDI
import di.LocalAppNavigator
import di.LocalAppProvidablesModule
import di.appModules
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import presentation.screen.MainScreen
import presentation.screen.tab.ProfileTab
import presentation.ui.theme.BubbleTheme



@Composable
@Preview
fun App() {
    val localAppProvidablesModule = LocalAppProvidablesModule()
    KoinApplication(application = {
        modules(
            localAppProvidablesModule + appModules()
        )
        KoinDI.init(this)
    }) {
        BubbleTheme {
            Navigator(MainScreen) { navigator: Navigator ->
                CompositionLocalProvider(
                    LocalAppNavigator provides navigator
                ) {
                    SlideTransition(navigator)
                }
            }
        }
    }
}