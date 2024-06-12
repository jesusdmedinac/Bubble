package presentation.screen.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import presentation.screen.tab.bubble.BubbleTabActions
import presentation.screen.tab.bubble.BubbleTabTitle

@Composable
fun BubbleTopAppBar() {
    Column {
        TopAppBar(
            title = {
                BubbleTabTitle()
            },
            actions = {
                BubbleTabActions()
            },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            modifier = Modifier.padding(8.dp)
        )
        Divider()
    }
}