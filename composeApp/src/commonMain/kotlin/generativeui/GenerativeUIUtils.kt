package generativeui

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle

@Composable
fun List<NodeModifier>.toModifier(): Modifier {
    var baseModifier: Modifier = Modifier
    forEach { nodeModifier ->
        baseModifier = when (nodeModifier) {
            NoNodeModifier -> Modifier
            is PaddingNodeModifier -> {
                nodeModifier withPadding baseModifier
            }

            is ClipNodeModifier -> {
                nodeModifier withClip baseModifier
            }

            is SizeNodeModifier -> {
                nodeModifier withSize baseModifier
            }
        }
    }
    return baseModifier
}

@Composable
fun TextNodeTypography.toStyle(): TextStyle = when (this) {
    TextNodeTypography.h1 -> MaterialTheme.typography.h1
    TextNodeTypography.h2 -> MaterialTheme.typography.h2
    TextNodeTypography.h3 -> MaterialTheme.typography.h3
    TextNodeTypography.h4 -> MaterialTheme.typography.h4
    TextNodeTypography.h5 -> MaterialTheme.typography.h5
    TextNodeTypography.h6 -> MaterialTheme.typography.h6
    TextNodeTypography.subtitle1 -> MaterialTheme.typography.subtitle1
    TextNodeTypography.subtitle2 -> MaterialTheme.typography.subtitle2
    TextNodeTypography.body1 -> MaterialTheme.typography.body1
    TextNodeTypography.body2 -> MaterialTheme.typography.body2
    TextNodeTypography.button -> MaterialTheme.typography.button
    TextNodeTypography.caption -> MaterialTheme.typography.caption
    TextNodeTypography.overline -> MaterialTheme.typography.overline
}

@Composable
fun ContentScaleImageAttribute.toContentScale(): ContentScale = when (this) {
    ContentScaleImageAttribute.None -> ContentScale.None
    ContentScaleImageAttribute.FillWidth -> ContentScale.FillWidth
    ContentScaleImageAttribute.FillHeight -> ContentScale.FillHeight
}