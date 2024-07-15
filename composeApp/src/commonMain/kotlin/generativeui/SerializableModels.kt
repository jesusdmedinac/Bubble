package generativeui

sealed interface Node {
    val nodeModifiers: List<NodeModifier>
}

sealed interface Layout : Node {
    val children: List<Node>
}

data class RowLayout(
    override val children: List<Node> = emptyList(),
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Layout

data class ColumnLayout(
    override val children: List<Node> = emptyList(),
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Layout

enum class TextNodeTypography {
    DisplayLarge,
    DisplayMedium,
    DisplaySmall,
    HeadlineLarge,
    HeadlineMedium,
    HeadlineSmall,
    TitleLarge,
    TitleMedium,
    TitleSmall,
    BodyLarge,
    BodyMedium,
    BodySmall,
    LabelLarge,
    LabelMedium,
    LabelSmall,
}

data class TextNode(
    val text: String,
    val style: TextNodeTypography,
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Node

sealed class ContentScaleImageAttribute {
    data object None : ContentScaleImageAttribute()
    data object FillWidth : ContentScaleImageAttribute()
    data object FillHeight : ContentScaleImageAttribute()
}

data class AsyncImageNode(
    val url: String,
    val contentDescription: String?,
    val contentScale: ContentScaleImageAttribute = ContentScaleImageAttribute.None,
    val alpha: Float = 1f,
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Node

data class BoxLayout(
    override val children: List<Layout> = emptyList(),
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Layout

data class CardLayout(
    override val children: List<Layout> = emptyList(),
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Layout

data class ButtonLayout(
    override val children: List<Layout> = emptyList(),
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Layout