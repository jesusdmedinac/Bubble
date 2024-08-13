package presentation.screen.tab.bubble

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
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
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.jetbrains.compose.resources.painterResource
import presentation.screenmodel.BubbleTopAppBarScreenModel

@Composable
fun RowScope.BubbleTabActions() {
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = navigator.koinNavigatorScreenModel<BubbleTopAppBarScreenModel>()
    val state by screenModel.container.stateFlow.collectAsState()
    TextButton(
        onClick = {},
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_chat_bubble),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = state.user.points.toString(),
            style = MaterialTheme.typography.titleLarge
        )
    }
    Spacer(modifier = Modifier.weight(1f))
    TextButton(
        onClick = {},
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary,
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_streak),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = state.user.streak.size.toString(),
            style = MaterialTheme.typography.titleLarge
        )
    }
}
