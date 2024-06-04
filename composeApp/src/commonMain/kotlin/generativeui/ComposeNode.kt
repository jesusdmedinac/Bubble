package generativeui

import androidx.compose.runtime.Composable

@Composable
fun ComposeNode(node: Node) {
    when (node) {
        is Layout -> {
            ComposeLayout(node)
        }

        is TextNode -> {
            ComposeTextNode(node)
        }

        is AsyncImageNode -> {
            ComposeAsyncImageNode(node)
        }
    }
}
