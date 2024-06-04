package generativeui


sealed interface Node {
    val nodeModifiers: List<NodeModifier>
}

sealed interface Layout : Node {
    val children: List<Layout>
}

data class RowLayout(
    override val children: List<Layout> = emptyList(),
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Layout

data class ColumnLayout(
    override val children: List<Layout> = emptyList(),
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Layout

enum class TextNodeTypography {
    h1, h2, h3, h4, h5, h6,
    subtitle1, subtitle2,
    body1, body2,
    button,
    caption,
    overline
}

data class TextNode(
    val text: String,
    val style: TextNodeTypography,
    override val nodeModifiers: List<NodeModifier> = emptyList(),
) : Node

sealed class ContentScaleImageAttribute {
    data object None : ContentScaleImageAttribute()
    data object FillWidth : ContentScaleImageAttribute()
}

data class AsyncImageNode(
    val url: String,
    val contentDescription: String?,
    val contentScale: ContentScaleImageAttribute = ContentScaleImageAttribute.None,
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