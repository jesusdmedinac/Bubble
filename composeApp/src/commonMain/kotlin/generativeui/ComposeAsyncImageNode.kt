package generativeui

import androidx.compose.runtime.Composable
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url

@Composable
fun ComposeAsyncImageNode(node: AsyncImageNode) {
    KamelImage(
        asyncPainterResource(data = Url(node.url)),
        contentDescription = node.contentDescription,
        modifier = node.nodeModifiers.toModifier(),
        contentScale = node.contentScale.toContentScale(),
    )
}