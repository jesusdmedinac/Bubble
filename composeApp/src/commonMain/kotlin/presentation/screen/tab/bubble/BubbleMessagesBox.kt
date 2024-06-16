package presentation.screen.tab.bubble

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import data.ChatAPI
import data.Message
import kotlinx.coroutines.launch
import presentation.model.UIMessage

@Composable
fun ColumnScope.BubbleMessagesBox(chatAPI: ChatAPI) {
    var uiMessageList: List<UIMessage> by remember { mutableStateOf(emptyList()) }
    val lazyListState = rememberLazyListState()
    LaunchedEffect(Unit) {
        if (uiMessageList.isNotEmpty()) {
            lazyListState.animateScrollToItem(uiMessageList.size - 1)
        }
    }
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()
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
                        .filter { it.author == "user" }
                        .size,
                    onValueChange = {
                        textFieldValue = it
                    },
                    onSendClick = {
                        coroutineScope.launch {
                            uiMessageList = listOf(
                                UIMessage(
                                    id = uiMessageList.size + 1,
                                    author = "user",
                                    body = it.text
                                )
                            ) + uiMessageList
                            val bubbleMessage = chatAPI.sendMessage(
                                uiMessageList.map { uiMessage ->
                                    Message(
                                        author = uiMessage.author,
                                        body = uiMessage.body
                                    )
                                }
                            )
                            uiMessageList = listOf(
                                UIMessage(
                                    id = uiMessageList.size + 1,
                                    author = bubbleMessage.author,
                                    body = bubbleMessage.body
                                )
                            ) + uiMessageList
                        }
                        textFieldValue = TextFieldValue("")
                    },
                )
            }

            items(uiMessageList) { message ->
                if (message.author == "user") {
                    BubblerMessageCard(message)
                } else {
                    BubbleMessageCard(message)
                }
            }
        }
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