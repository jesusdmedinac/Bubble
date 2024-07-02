package presentation.screen.tab.bubble

import LocalAnalytics
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.minimumInteractiveComponentSize
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.ic_message
import bubble.composeapp.generated.resources.ic_send
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BubbleTextField(
    value: TextFieldValue,
    remainingFreeMessages: Int = 0,
    isSendingMessage: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit,
    onSendClick: (TextFieldValue) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val analytics = LocalAnalytics.current
    val noMoreMessages = remainingFreeMessages <= 0
    val isSendingMessageEnabled = !isSendingMessage && !noMoreMessages && value.text.isNotEmpty()
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                enabled = !noMoreMessages
            ) {
                Icon(
                    painterResource(Res.drawable.ic_message),
                    contentDescription = null
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp,
                            bottomStart = 40.dp,
                            bottomEnd = 40.dp
                        )
                    )
                    .background(MaterialTheme.colors.secondary)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Tienes $remainingFreeMessages mensajes restantes con Bubble",
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onSecondary
                    )
                    Icon(
                        painterResource(Res.drawable.ic_chat_bubble),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (!noMoreMessages) MaterialTheme.colors.primary else
                            MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
                                .compositeOver(MaterialTheme.colors.surface)
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (value.text.isEmpty()) {
                        val style = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onPrimary.copy(
                                alpha = 0.7f
                            ),
                        )
                        Text(
                            text = "¿Qué estás pensando?",
                            style = style,
                        )
                    }
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.body1.copy(
                            color = MaterialTheme.colors.onPrimary,
                        ),
                        enabled = !noMoreMessages
                    )
                }
                var bool by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    while (true) {
                        bool = !bool
                        delay(1000)
                    }
                }
                IconButton(
                    onClick = {
                        analytics.sendClickEvent(value.text.length.toLong())
                        onSendClick(value)
                    },
                    enabled = isSendingMessageEnabled
                ) {
                    AnimatedVisibility(
                        isSendingMessage,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .size(24.dp),
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                    AnimatedVisibility(
                        !isSendingMessage,
                        enter = fadeIn(),
                        exit = fadeOut(),
                    ) {
                        Icon(
                            painterResource(Res.drawable.ic_send),
                            contentDescription = null,
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}