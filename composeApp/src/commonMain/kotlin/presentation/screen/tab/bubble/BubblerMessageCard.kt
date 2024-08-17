package presentation.screen.tab.bubble

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_message_corner
import com.mikepenz.markdown.m2.Markdown
import com.mikepenz.markdown.m2.markdownColor
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import presentation.model.UIBubblerMessage
import presentation.model.UIMessage

@Composable
fun BubblerMessageCard(uiMessage: UIBubblerMessage) {
    val primary = MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Markdown(
            uiMessage.body.message,
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(start = 64.dp)
                .padding(end = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(primary)
                .padding(12.dp)
                .align(Alignment.CenterEnd),
            colors = markdownColor(
                text = MaterialTheme.colorScheme.onPrimary
            )
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(y = 0.dp, x = (-8).dp)
        ) {
            Icon(
                painterResource(Res.drawable.ic_message_corner),
                contentDescription = null,
                modifier = Modifier
                    .size(16.dp),
                tint = primary
            )
        }
    }
}