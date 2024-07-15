package presentation.screen.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.tab_title_bubble
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import data.local.ConnectionState
import di.LocalNetworkAPI
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.screen.tab.bubble.BubbleMessagesBox

object BubbleTab : Tab {
    private val connectionState = mutableStateOf(ConnectionState.Idle)

    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_title_bubble)
            val icon = painterResource(Res.drawable.ic_chat_bubble)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val networkAPI = LocalNetworkAPI.current
        var connectionState: ConnectionState by remember { connectionState }
        LaunchedEffect(Unit) {
            while (true) {
                delay(1000)
                networkAPI.isConnected {
                    connectionState = if (it) ConnectionState.Connected
                    else ConnectionState.Disconnected
                }
            }
        }
        BubbleMessagesBox(
            connectionState
        )
    }
}