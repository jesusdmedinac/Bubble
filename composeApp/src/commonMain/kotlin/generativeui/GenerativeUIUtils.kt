package generativeui

import androidx.compose.material3.MaterialTheme
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
    TextNodeTypography.DisplayLarge -> MaterialTheme.typography.displayLarge
    TextNodeTypography.DisplayMedium -> MaterialTheme.typography.displayMedium
    TextNodeTypography.DisplaySmall -> MaterialTheme.typography.displaySmall
    TextNodeTypography.HeadlineLarge -> MaterialTheme.typography.headlineLarge
    TextNodeTypography.HeadlineMedium -> MaterialTheme.typography.headlineMedium
    TextNodeTypography.HeadlineSmall -> MaterialTheme.typography.headlineSmall
    TextNodeTypography.TitleLarge -> MaterialTheme.typography.titleLarge
    TextNodeTypography.TitleMedium -> MaterialTheme.typography.titleMedium
    TextNodeTypography.TitleSmall -> MaterialTheme.typography.titleSmall
    TextNodeTypography.BodyLarge -> MaterialTheme.typography.bodyLarge
    TextNodeTypography.BodyMedium -> MaterialTheme.typography.bodyMedium
    TextNodeTypography.BodySmall -> MaterialTheme.typography.bodySmall
    TextNodeTypography.LabelLarge -> MaterialTheme.typography.labelLarge
    TextNodeTypography.LabelMedium -> MaterialTheme.typography.labelMedium
    TextNodeTypography.LabelSmall -> MaterialTheme.typography.labelSmall
}

@Composable
fun ContentScaleImageAttribute.toContentScale(): ContentScale = when (this) {
    ContentScaleImageAttribute.None -> ContentScale.None
    ContentScaleImageAttribute.FillWidth -> ContentScale.FillWidth
    ContentScaleImageAttribute.FillHeight -> ContentScale.FillHeight
}