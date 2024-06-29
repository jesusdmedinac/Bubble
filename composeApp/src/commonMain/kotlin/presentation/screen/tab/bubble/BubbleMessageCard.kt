package presentation.screen.tab.bubble

import LocalSendingData
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_message_corner
import com.mikepenz.markdown.m2.Markdown
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import presentation.model.UIBubbleMessage
import presentation.model.UIMessage

@Composable
fun BubbleMessageCard(uiMessage: UIBubbleMessage) {
    val secondary = MaterialTheme.colors.secondary
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart,
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .padding(start = 16.dp)
                .padding(end = 64.dp)
                .clip(RoundedCornerShape(topEnd = 50.dp, bottomEnd = 50.dp))
                .clip(RoundedCornerShape(topStart = 8.dp, bottomStart = 0.dp))
                .background(secondary)
                .padding(12.dp),
        ) {
            Markdown(uiMessage.body.message)
            uiMessage
                .body
                .challenge
                ?.let { challenge ->
                    Card(
                        modifier = Modifier,
                        shape = RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 8.dp,
                            bottomEnd = 50.dp,
                        ),
                    ) {
                        Column {
                            KamelImage(
                                asyncPainterResource(challenge.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.FillWidth
                            )
                            Text(
                                challenge.name,
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 8.dp,
                                    bottom = 4.dp,
                                ),
                            )
                            Text(
                                challenge.description,
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 4.dp,
                                    bottom = 4.dp,
                                ),
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = 8.dp,
                                    ),
                            ) {
                                val sendingData = LocalSendingData.current
                                Button(onClick = {
                                    sendingData.sendPlainText(challenge.shareText)
                                }) {
                                    Text("¡Tomar el reto!")
                                }
                            }
                        }
                    }
                }
        }

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