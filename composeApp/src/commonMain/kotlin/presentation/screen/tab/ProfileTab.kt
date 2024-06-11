package presentation.screen.tab

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.tab_title_home
import bubble.composeapp.generated.resources.tab_title_profile
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.stringResource
import presentation.screen.tab.bubble.BubbleTabActions
import presentation.screen.tab.bubble.BubbleTabTitle

@OptIn(ExperimentalMaterialApi::class)
object ProfileTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_title_profile)
            val icon = rememberVectorPainter(Icons.Default.Person)

            return remember {
                TabOptions(
                    index = 2u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        Column {
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
            Divider()
            LazyColumn {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        ) {

                        }
                        Column {
                            Text(
                                "Nombre del usuario",
                                style = MaterialTheme.typography.subtitle1,
                            )
                            Text(
                                "Correo electrónico",
                                style = MaterialTheme.typography.subtitle2,
                            )
                        }
                    }
                }
                item {
                    Text(
                        text = "Tiempo en pantalla",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = "Hoy, 10 de junio",
                                style = MaterialTheme.typography.subtitle2,
                            )
                            Text(
                                text = "1 h 39 min",
                                style = MaterialTheme.typography.h4,
                            )
                            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(128.dp)
                                    .drawBehind {
                                        drawRect(
                                            color = Color.White,
                                            topLeft = Offset(0f, 0f),
                                            size = size,
                                        )
                                        repeat(6) {
                                            drawLine(
                                                color = Color.Black,
                                                start = Offset(0f, (size.height / 6) * it),
                                                end = Offset(size.width, (size.height / 6) * it),
                                                strokeWidth = 2f
                                            )
                                        }
                                        repeat(9) {
                                            drawLine(
                                                color = Color.Black,
                                                start = Offset((size.width / 8) * it, 0f),
                                                end = Offset((size.width / 8) * it, size.height),
                                                strokeWidth = 2f,
                                                pathEffect = pathEffect
                                            )
                                        }
                                    }
                            ) {
                            }
                        }
                    }
                }
                item {
                    Text(
                        "Actualizado: hoy, 11:09 p.m.",
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Más usadas")
                        Spacer(modifier = Modifier.weight(1f))
                        TextButton(
                            onClick = { /*TODO*/ },
                        ) {
                            Text("MOSTRAR CATEGORÍAS")
                        }
                    }
                }
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val stringList = listOf(
                                "YouTube",
                                "YouTube",
                                "YouTube",
                                "YouTube",
                                "YouTube",
                                "YouTube",
                            )
                            stringList
                                .forEachIndexed { index, it ->
                                    Row(
                                        modifier = Modifier.clickable { },
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Default.Home,
                                            contentDescription = null,
                                            modifier = Modifier.padding(16.dp)
                                        )
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(it)
                                            Text("1 h 9 min")
                                        }
                                        IconButton(onClick = {}) {
                                            Icon(
                                                Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                                contentDescription = null,
                                                modifier = Modifier.padding(16.dp)
                                            )
                                        }
                                    }
                                    Divider(
                                        modifier = Modifier.padding(start = 56.dp)
                                    )
                                }
                            ListItem(
                                modifier = Modifier.clickable { }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Spacer(modifier = Modifier.width(42.dp))
                                    Text("Mostrar más", color = MaterialTheme.colors.primary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}