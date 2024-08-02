import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import data.remote.ChatAIAPI
import dev.gitlive.firebase.database.FirebaseDatabase
import di.KoinDI
import di.KoinDI.get
import di.LocalAppNavigator
import di.LocalAppProvidablesModule
import di.appModules
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.screen.MainScreen
import presentation.ui.theme.BubbleTheme

@Composable
@Preview
fun App(
    chatModuleBlock: (FirebaseDatabase) -> ChatAIAPI = { ChatAIAPI.Default }
) {
    val localAppProvidablesModule = LocalAppProvidablesModule()
    KoinApplication(application = {
        modules(
            module { single<ChatAIAPI> { chatModuleBlock(get()) } } +
                    localAppProvidablesModule +
                    appModules()
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