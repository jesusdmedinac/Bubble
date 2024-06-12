package generativeui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun ComposeColumn(layout: ColumnLayout) {
    Column(
        modifier = layout.nodeModifiers.toModifier()
    ) {
        layout.children.forEach { child ->
            ComposeNode(child)
        }
    }
}