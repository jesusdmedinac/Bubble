package presentation.screen.tab.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_message_corner
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import presentation.model.UIMessage

@Composable
fun BubbleMessageCard(uiMessage: UIMessage) {
    val secondary = MaterialTheme.colors.secondary
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Text(
            uiMessage.body,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(start = 16.dp)
                .padding(end = 64.dp)
                .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 0.dp))
                .background(secondary)
                .padding(12.dp),
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onSecondary
            )
        )

        Box(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 4.dp
                )
                .padding(top = 34.dp)
                .fillMaxHeight()
                .width(20.dp)
        ) {
            Icon(
                painterResource(Res.drawable.ic_message_corner),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
                    .graphicsLayer {
                        rotationY = 180f
                    },
                tint = secondary
            )
        }
    }
}