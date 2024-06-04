package presentation.screen.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.tab_title_home
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import generativeui.AsyncImageNode
import generativeui.ClipNodeModifier
import generativeui.ComposeAsyncImageNode
import generativeui.ComposeNode
import generativeui.ComposeTextNode
import generativeui.ContentScaleImageAttribute
import generativeui.PaddingNodeModifier
import generativeui.ShapeNode
import generativeui.SizeNodeModifier
import generativeui.TextNode
import generativeui.TextNodeTypography
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import org.jetbrains.compose.resources.stringResource

object HomeTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val title = stringResource(Res.string.tab_title_home)
            val icon = rememberVectorPainter(Icons.Default.Home)

            return remember {
                TabOptions(
                    index = 0u,
                    title = title,
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        LazyColumn {
            item {
                Column {
                    ComposeNode(
                        TextNode(
                            "Categorias",
                            nodeModifiers = listOf(
                                PaddingNodeModifier(
                                    top = 8f,
                                    bottom = 8f,
                                    start = 8f,
                                    end = 8f,
                                ),
                            ),
                            style = TextNodeTypography.h6,
                        )
                    )
                    LazyRow {
                        items(
                            listOf(
                                "Todo",
                                "Lectura",
                                "Aire libre",
                                "Arte",
                                "Ejercicio y bienestar físico",
                                "Manualidades y proyectos DIY",
                                "Cocina y gastronomía",
                                "Voluntariado y comunidad",
                                "Desarrollo personal y aprendizaje",
                            )
                        ) { category ->
                            Column(
                                modifier = Modifier.width(96.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colors.primary),
                                ) {
                                    Text(
                                        category[0].toString(),
                                        color = MaterialTheme.colors.onPrimary,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                                Text(category, fontSize = 12.sp, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
            item {
                ComposeTextNode(
                    TextNode(
                        "Continuar Reto",
                        nodeModifiers = listOf(
                            PaddingNodeModifier(
                                top = 8f,
                                bottom = 8f,
                                start = 8f,
                                end = 8f,
                            )
                        ),
                        style = TextNodeTypography.h6,
                    )
                )
                LazyRow {
                    item {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    items(
                        (0..10).toList()
                    ) {
                        Box(
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Card {
                                Column {
                                    ComposeAsyncImageNode(
                                        AsyncImageNode(
                                            url = "https://picsum.photos/id/${it}/200/300",
                                            contentDescription = null,
                                            nodeModifiers = listOf(
                                                SizeNodeModifier(width = 256f, height = 128f),
                                                ClipNodeModifier(ShapeNode.RoundedCorner(8f)),
                                            ),
                                            contentScale = ContentScaleImageAttribute.FillWidth
                                        )
                                    )
                                    Text(
                                        "Reto $it",
                                        style = MaterialTheme.typography.h6,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                    Text(
                                        "Descripción del reto $it",
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(8.dp)
                                    )
                                }
                            }
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                }
            }
        }
    }
}
