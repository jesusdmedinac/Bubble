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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_message_corner
import org.jetbrains.compose.resources.painterResource
import presentation.screen.tab.UIMessage

@Composable
fun BubblerMessageCard(uiMessage: UIMessage) {
    val primary = MaterialTheme.colors.primary
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterEnd,
    ) {
        Text(
            uiMessage.body,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(start = 64.dp)
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 0.dp))
                .clip(RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp))
                .background(primary)
                .padding(12.dp),
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary
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
                    .size(20.dp),
                tint = primary
            )
        }
    }
}