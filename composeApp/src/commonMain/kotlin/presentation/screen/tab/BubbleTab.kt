package presentation.screen.tab

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.ic_help
import bubble.composeapp.generated.resources.ic_message
import bubble.composeapp.generated.resources.ic_premium
import bubble.composeapp.generated.resources.ic_send
import bubble.composeapp.generated.resources.ic_settings
import bubble.composeapp.generated.resources.tab_title_bubble
import bubble.composeapp.generated.resources.tab_title_home
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
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
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopAppBar(
                title = {
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
                },
                actions = {
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
                },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                modifier = Modifier.padding(8.dp)
            )
            Divider(modifier = Modifier.fillMaxWidth())
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

            }
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
                                "Tienes 50 mensajes restantes con Bubble",
                                style = MaterialTheme.typography.caption,
                            )
                            Icon(
                                painterResource(Res.drawable.ic_chat_bubble),
                                contentDescription = null,
                                modifier = Modifier.size(12.dp)
                            )
                        }
                    }
                    TextField(
                        value = "",
                        onValueChange = {},
                        placeholder = {
                            Text(text = "¿Qué estás pensando?")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape),
                        trailingIcon = {
                            IconButton(onClick = {}) {
                                Icon(
                                    painterResource(Res.drawable.ic_send),
                                    contentDescription = null
                                )
                            }
                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = MaterialTheme.colors.primary,
                        )
                    )
                }
            }
        }
    }
}