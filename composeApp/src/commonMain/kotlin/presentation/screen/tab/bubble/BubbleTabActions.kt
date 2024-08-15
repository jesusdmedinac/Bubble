package presentation.screen.tab.bubble

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.ic_streak
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import org.jetbrains.compose.resources.painterResource
import presentation.screen.tab.ProfileTab
import presentation.screenmodel.BubbleTopAppBarScreenModel
import presentation.screenmodel.BubbleTopAppBarState

@Composable
fun BubbleTabActions() {
    val navigator = LocalNavigator.currentOrThrow
    val tabNavigator = LocalTabNavigator.current
    val screenModel = navigator.getNavigatorScreenModel<BubbleTopAppBarScreenModel>()
    val state by screenModel.container.stateFlow.collectAsState()
    TextButton(
        onClick = {
            tabNavigator.current = ProfileTab
        },
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_chat_bubble),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = state.user.formattedPoints,
            style = MaterialTheme.typography.bodyLarge
        )
    }
    Spacer(modifier = Modifier.width(8.dp))
    TextButton(
        onClick = {
            tabNavigator.current = ProfileTab
        },
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_streak),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = state.user.formattedStreak,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
