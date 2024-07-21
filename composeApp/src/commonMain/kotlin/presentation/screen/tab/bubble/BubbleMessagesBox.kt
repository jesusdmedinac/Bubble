package presentation.screen.tab.bubble

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import data.local.ConnectionState
import data.local.HasUsagePermissionState
import di.LocalAppNavigator
import kotlinx.coroutines.launch
import presentation.model.UIBubbleMessage
import presentation.model.UIBubblerMessage
import presentation.model.UICallToActionType
import presentation.model.UIMessageBody
import presentation.screen.PaywallScreen
import presentation.screenmodel.BubbleTabScreenModel
import presentation.screenmodel.BubbleTabState

@Composable
fun BubbleMessagesBox(
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val localSoftwareKeyboardController = LocalSoftwareKeyboardController.current
    val appNavigator = LocalAppNavigator.currentOrThrow
    val navigator = LocalNavigator.currentOrThrow
    val screenModel = navigator.getNavigatorScreenModel<BubbleTabScreenModel>()
    val state: BubbleTabState by screenModel.container.stateFlow.collectAsState()
    val lazyListState = rememberLazyListState()
    val messages = state.messages
    val remainingFreeMessages = state.remainingFreeMessages
    var currentTextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                localSoftwareKeyboardController?.hide()
            },
        contentAlignment = Alignment.Center,
    ) {
        LaunchedEffect(Unit) {
            if (messages.isNotEmpty()) {
                lazyListState.animateScrollToItem(0)
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = lazyListState,
            reverseLayout = true,
        ) {
            when (state.connectionState) {
                ConnectionState.Idle -> Unit
                ConnectionState.Connected -> item {
                    BubbleTextField(
                        value = currentTextFieldValue,
                        remainingFreeMessages = remainingFreeMessages,
                        isSendingMessage = state.loading,
                        onValueChange = {
                            currentTextFieldValue = it
                        },
                        onSendClick = { textFieldValue ->
                            screenModel.sendMessage(textFieldValue.text)
                            currentTextFieldValue = TextFieldValue("")
                        },
                        modifier = Modifier
                            .let {
                                if (remainingFreeMessages <= 0) {
                                    it.clickable(
                                        interactionSource = interactionSource,
                                        indication = null,
                                    ) {
                                        appNavigator.push(PaywallScreen)
                                    }
                                } else it
                            }
                    )
                }
                ConnectionState.Disconnected -> item {
                    BubbleMessageCard(
                        UIBubbleMessage(
                            id = 0,
                            author = "model",
                            body = UIMessageBody(
                                """
                                    ¡Ups! parece que no hay conexión a internet. Actívalo para poder seguir nuestra conversación
                                """.trimIndent()
                            )
                        )
                    )
                }
            }

            when (state.hasUsagePermissionState) {
                HasUsagePermissionState.Idle -> Unit
                HasUsagePermissionState.Granted -> Unit
                HasUsagePermissionState.Denied -> {
                    item {
                        BubbleMessageCard(
                            UIBubbleMessage(
                                id = 0,
                                author = "model",
                                body = UIMessageBody(
                                    """
                                    ¡Ups! parece que no tengo permiso de revisar tu tiempo en pantalla. Puedes activar esta función en "Acceso al uso"
                                """.trimIndent(),
                                    callToAction = UICallToActionType.REQUEST_USAGE_ACCESS_SETTINGS
                                )
                            )
                        )
                    }
                }
            }

            items(messages) { message ->
                when (message) {
                    is UIBubbleMessage -> BubbleMessageCard(message)
                    is UIBubblerMessage -> BubblerMessageCard(message)
                }
            }
        }
        androidx.compose.animation.AnimatedVisibility(
            lazyListState.canScrollBackward,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(0)
                    }
                }, modifier = Modifier
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