package presentation.screen.tab.bubble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_message_corner
import com.mikepenz.markdown.m2.Markdown
import com.mikepenz.markdown.m2.markdownColor
import di.LocalSendingData
import di.LocalUsageAPI
import org.jetbrains.compose.resources.painterResource
import presentation.model.UIBubbleMessage

@Composable
fun BubbleMessageCard(uiMessage: UIBubbleMessage) {
    val usageAPI = LocalUsageAPI.current
    val secondary = MaterialTheme.colorScheme.secondary
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
            Markdown(
                uiMessage.body.message,
                colors = markdownColor(
                    text = MaterialTheme.colorScheme.onPrimary
                ),
            )
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
                            /*KamelImage(
                                asyncPainterResource(challenge.image),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.FillWidth
                            )*/
                            Text(
                                challenge.name,
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 8.dp,
                                    bottom = 4.dp,
                                ),
                            )
                            Text(
                                challenge.description,
                                style = MaterialTheme.typography.bodyLarge,
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
            uiMessage
                .body
                .callToAction
                ?.let {
                    Button(onClick = {
                        usageAPI.requestUsageSettings()
                    }) {
                        Text("Acceso al uso")
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