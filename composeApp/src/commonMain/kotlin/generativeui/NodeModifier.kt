package generativeui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

sealed class NodeModifier

object NoNodeModifier : NodeModifier()

data class PaddingNodeModifier(
    val top: Float = 0f,
    val bottom: Float = 0f,
    val start: Float = 0f,
    val end: Float = 0f,
) : NodeModifier()

data class SizeNodeModifier(
    val width: Float = 0f,
    val height: Float = 0f,
) : NodeModifier()

sealed class ShapeNode {
    data class RoundedCorner(
        val radius: Float,
    ) : ShapeNode()
}

data class ClipNodeModifier(
    val shape: ShapeNode,
) : NodeModifier()

infix fun PaddingNodeModifier.withPadding(modifier: Modifier): Modifier = modifier.padding(
    top = top.dp,
    bottom = bottom.dp,
    start = start.dp,
    end = end.dp,
)

infix fun SizeNodeModifier.withSize(modifier: Modifier): Modifier = modifier.size(
    width = width.dp,
    height = height.dp
)

infix fun ClipNodeModifier.withClip(modifier: Modifier): Modifier = modifier.clip(
    when (shape) {
        is ShapeNode.RoundedCorner -> RoundedCornerShape(shape.radius.dp)
    }
)