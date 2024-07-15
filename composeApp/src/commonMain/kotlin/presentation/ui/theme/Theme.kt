package presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.materialkolor.PaletteStyle
import com.materialkolor.dynamicColorScheme

@Composable
fun BubbleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme: ColorScheme =
        dynamicColorScheme(AzulMedio2, darkTheme, isAmoled = false, style = PaletteStyle.Fidelity)
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}