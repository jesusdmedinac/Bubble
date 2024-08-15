package presentation.model

import androidx.compose.ui.graphics.Color

/**
 * TrendTimeInForeground represents the trend of time in foreground.
 */
enum class TrendTimeInForeground(
    val degrees: Float,
    val color: Color,
    val label: String = "",
) {
    RAPIDLY_RISING(
        degrees = -90f,
        color = DarkRed,
        label = "Incremento rápido",
    ),
    RISING(
        degrees = -60f,
        color = Red,
        label = "Incremento",
    ),
    SLOWLY_RISING(
        degrees = -30f,
        color = Yellow,
        label = "Incremento lento",
    ),
    STABLE(
        degrees = 0f,
        color = Blue,
        label = "Estable",
    ),
    SLOWLY_FALLING(
        degrees = 30f,
        color = LightBlue,
        label = "Decremento lento",
    ),
    FALLING(
        degrees = 60f,
        color = DarkBlue,
        label = "Decremento",
    ),
    RAPIDLY_FALLING(
        degrees = 90f,
        color = Green,
        label = "Decremento rápido",
    ),
}

val DarkRed = Color(0xFFB71C1C)
val Red = Color(0xFFF44336)
val Yellow = Color(0xFFFBC02D)
val Blue = Color(0xFF2196F3)
val LightBlue = Color(0xFF03A9F4)
val DarkBlue = Color(0xFF0D47A1)
val Green = Color(0xFF4CAF50)