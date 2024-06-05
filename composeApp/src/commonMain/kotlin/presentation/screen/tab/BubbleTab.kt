package presentation.screen.tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.ic_help
import bubble.composeapp.generated.resources.ic_message
import bubble.composeapp.generated.resources.ic_message_corner
import bubble.composeapp.generated.resources.ic_premium
import bubble.composeapp.generated.resources.ic_send
import bubble.composeapp.generated.resources.ic_settings
import bubble.composeapp.generated.resources.tab_title_bubble
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

object BubbleTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_title_bubble)
            val icon = painterResource(Res.drawable.ic_chat_bubble)

            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        val interactionSource = remember { MutableInteractionSource() }
        val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
        Column(
            modifier = Modifier.fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    localSoftwareKeyboardController?.hide()
                }
        ) {
            TopAppBar(
                title = {
                    BubbleTabTitle()
                },
                actions = {
                    BubbleTabActions()
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier.padding(8.dp)
            )
            Divider(modifier = Modifier.fillMaxWidth())
            BubbleMessagesBox()
        }
    }

    @Composable
    private fun ColumnScope.BubbleMessagesBox() {
        var uiMessageList: List<UIMessage> by remember { mutableStateOf(emptyList()) }
        val lazyListState = rememberLazyListState()
        LaunchedEffect(Unit) {
            if (uiMessageList.isNotEmpty()) {
                lazyListState.animateScrollToItem(uiMessageList.size - 1)
            }
        }
        var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomEnd,
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                state = lazyListState,
                reverseLayout = true,
            ) {
                item {
                    BubbleTextField(
                        value = textFieldValue,
                        remainingFreeMessages = 50 - uiMessageList
                            .filter { it.author == "Bubbler" }
                            .size,
                        onValueChange = {
                            textFieldValue = it
                        },
                        onSendClick = {
                            uiMessageList = listOf(
                                UIMessage(
                                    id = uiMessageList.size + 1,
                                    author = "Bubbler",
                                    body = it.text
                                )
                            ) + uiMessageList
                            textFieldValue = TextFieldValue("")
                            uiMessageList = listOf(
                                UIMessage(
                                    id = uiMessageList.size + 1,
                                    author = "Bubble",
                                    body = "Hola Bubbler"
                                )
                            ) + uiMessageList
                        },
                    )
                }

                items(uiMessageList) { message ->
                    if (message.author == "Bubbler") {
                        BubblerMessageCard(message)
                    } else {
                        BubbleMessageCard(message)
                    }
                }
            }
            val coroutineScope = rememberCoroutineScope()
            androidx.compose.animation.AnimatedVisibility(
                lazyListState.canScrollBackward,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }, modifier = Modifier
                        .padding(16.dp)
                        .clip(CircleShape)
                ) {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        }
    }

    @Composable
    private fun BubbleTextField(
        value: TextFieldValue,
        remainingFreeMessages: Int = 0,
        onValueChange: (TextFieldValue) -> Unit,
        onSendClick: (TextFieldValue) -> Unit = {}
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
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
                            "Tienes ${remainingFreeMessages} mensajes restantes con Bubble",
                            style = MaterialTheme.typography.caption,
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
                        .background(MaterialTheme.colors.primary)
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
                            )
                        )
                    }
                    IconButton(onClick = {
                        onSendClick(value)
                    }) {
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

    @Composable
    private fun RowScope.BubbleTabActions() {
        Button(
            onClick = {},
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painterResource(Res.drawable.ic_premium),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painterResource(Res.drawable.ic_settings),
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Button(
            onClick = {},
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            contentPadding = PaddingValues(0.dp)
        ) {
            Icon(
                painterResource(Res.drawable.ic_help),
                contentDescription = null
            )
        }
    }

    @Composable
    private fun BubbleTabTitle() {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painterResource(Res.drawable.ic_chat_bubble),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = stringResource(Res.string.tab_title_bubble))
        }
    }

    @Composable
    private fun BubbleMessageCard(uiMessage: UIMessage) {
        val secondary = MaterialTheme.colors.secondary
        var text by remember { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(uiMessage) {
            coroutineScope.launch {
                repeat(3) {
                    text = "Escribiendo"
                    "...".forEach {
                        delay(200)
                        text += it
                    }
                    delay(200)
                }
                text = ""
                uiMessage.body.forEach {
                    delay(50)
                    text += it
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text,
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

    @Composable
    private fun BubblerMessageCard(uiMessage: UIMessage) {
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
}