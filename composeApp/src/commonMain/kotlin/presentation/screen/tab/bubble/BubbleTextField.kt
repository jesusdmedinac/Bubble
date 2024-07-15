package presentation.screen.tab.bubble

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
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
import di.LocalAnalytics
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

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
        Column(
            modifier = Modifier
                .height(88.dp)
                .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                onClick = {},
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape),
                enabled = !noMoreMessages,
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(
                    painterResource(Res.drawable.ic_message),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
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
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(horizontal = 8.dp, vertical = 2.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Tienes $remainingFreeMessages mensajes restantes con Bubble",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Icon(
                        painterResource(Res.drawable.ic_chat_bubble),
                        contentDescription = null,
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (!noMoreMessages) MaterialTheme.colorScheme.primary else
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                                .compositeOver(MaterialTheme.colorScheme.surface)
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (value.text.isEmpty()) {
                        val style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary.copy(
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
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
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
                            color = MaterialTheme.colorScheme.onPrimary
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
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}