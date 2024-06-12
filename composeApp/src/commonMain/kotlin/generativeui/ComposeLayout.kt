package generativeui

import androidx.compose.runtime.Composable

@Composable
fun ComposeLayout(layout: Layout) {
    when (layout) {
        is BoxLayout -> TODO()
        is ButtonLayout -> TODO()
        is CardLayout -> TODO()
        is ColumnLayout -> ComposeColumn(layout)
        is RowLayout -> TODO()
    }
}