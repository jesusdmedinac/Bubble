import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import com.jesusdmedinac.bubble.BuildKonfig
import data.remote.FirebaseConfig
import data.remote.GeminiAPI
import data.remote.GeminiConfig
import di.KoinDI
import di.LocalAppNavigator
import di.LocalAppProvidablesModule
import di.appModules
import di.chatAIAPI
import di.serverModules
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.dsl.module
import presentation.screen.MainScreen
import presentation.ui.theme.BubbleTheme

@Composable
@Preview
fun App() {
    val localAppProvidablesModule = LocalAppProvidablesModule()
    KoinApplication(application = {
        modules(
            localAppProvidablesModule +
                    module {
                        single {
                            FirebaseConfig(
                                databaseName = BuildKonfig.firebaseDatabaseName,
                                webApiKey = BuildKonfig.firebaseWebKey
                            )
                        }
                        single {
                            GeminiConfig(
                                key = BuildKonfig.geminiKey
                            )
                        }
                    } +
                    serverModules() +
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