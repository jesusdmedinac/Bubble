package generativeui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun ComposeTextNode(node: TextNode) {
    Text(
        node.text,
        style = node.style.toStyle(),
        modifier = node.nodeModifiers.toModifier()
    )
}