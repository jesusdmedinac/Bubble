package presentation.screen.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.tab_title_bubble
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.screen.tab.bubble.BubbleMessagesBox

object BubbleTab : Tab {
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
        BubbleMessagesBox()
    }
}
