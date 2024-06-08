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
import kotlinx.coroutines.launch
import presentation.screen.tab.UIMessage

@Composable
fun ColumnScope.BubbleMessagesBox() {
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