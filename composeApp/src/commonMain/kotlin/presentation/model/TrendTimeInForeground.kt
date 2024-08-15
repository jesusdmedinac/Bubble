package presentation.model

/**
 * TrendTimeInForeground represents the trend of time in foreground.
 *
 */
enum class TrendTimeInForeground(
    val degrees: Float,
) {
    RAPIDLY_RISING(-90f),
    RISING(-60f),
    SLOWLY_RISING(-30f),
    STABLE(0f),
    SLOWLY_FALLING(30f),
    FALLING(60f),
    RAPIDLY_FALLING(90f),
}
