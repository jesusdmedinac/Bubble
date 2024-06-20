import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import data.ChatAPI
import data.Message

fun MainViewController(chatAPI: ChatAPI) = ComposeUIViewController { App(chatAPI) }