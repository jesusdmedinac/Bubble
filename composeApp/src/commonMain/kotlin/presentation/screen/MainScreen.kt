package presentation.screen

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class MainScreen : Screen {
    @Composable
    override fun Content() {
        Text("Main Screen")
    }
}