package presentation.screen.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.tab_title_bubble
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.screen.tab.bubble.BubbleMessagesBox
import presentation.screen.tab.bubble.BubbleTabActions
import presentation.screen.tab.bubble.BubbleTabTitle

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
        val interactionSource = remember { MutableInteractionSource() }
        val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier.fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    localSoftwareKeyboardController?.hide()
                }
        ) {
            BubbleMessagesBox()
        }
    }
}