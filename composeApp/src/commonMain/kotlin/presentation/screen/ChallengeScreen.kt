package presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.currentOrThrow
import generativeui.ComposeTextNode
import generativeui.PaddingNodeModifier
import generativeui.TextNode
import generativeui.TextNodeTypography
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import presentation.model.ChallengeCategory
import presentation.model.UICard
import presentation.model.UIChallenge

class ChallengeScreen(
    private val challenge: UIChallenge
) : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val navigator = LocalAppNavigator.currentOrThrow
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.pop()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        KamelImage(
                            asyncPainterResource(data = Url(challenge.image)),
                            contentDescription = challenge.description,
                            modifier = Modifier
                                .padding(
                                    end = 8.dp,
                                    top = 8.dp,
                                    bottom = 8.dp
                                )
                                .clip(CircleShape)
                                .size(64.dp)
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop,
                        )
                        Column {
                            Text(
                                challenge.name,
                                style = MaterialTheme.typography.subtitle1,
                            )
                            Text(
                                challenge.description,
                                style = MaterialTheme.typography.subtitle2,
                            )
                        }
                    }
                }
                item {
                    Button(onClick = {}) {
                        Text("Iniciar Reto")
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Forward"
                        )
                    }
                }
                item {
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                    Row(
                        modifier = Modifier
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "2k calificaciones",
                                style = MaterialTheme.typography.caption,
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "4.5",
                                    style = MaterialTheme.typography.h5,
                                )
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = "Rating",
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(1.dp)
                                .padding(vertical = 16.dp)
                        )
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                "2k completadas",
                                style = MaterialTheme.typography.caption,
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Text(
                                    "4/5",
                                    style = MaterialTheme.typography.h5,
                                )
                                Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Person",
                                )
                            }
                            Text(
                                "Personas completan el reto",
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }
                    Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
                item {
                    CardsSection(
                        title = "Recompensas",
                        cards = (0..10)
                            .toList()
                            .map {
                                object : UICard {
                                    override val id: Int = it
                                    override val name: String = "Recompensa $it"
                                    override val description: String =
                                        "Descripción de la recompensa $it"
                                    override val image: String =
                                        "https://picsum.photos/id/${it}/200/300"
                                }
                            },
                    )
                }

                item {
                    ComposeTextNode(
                        TextNode(
                            "Califica este reto",
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
                    Row {
                        var currentRate by remember { mutableStateOf(0) }
                        val maxRate = 5
                        repeat(maxRate) { rate ->
                            IconButton(onClick = {
                                currentRate = rate + 1
                            }) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (rate < currentRate) {
                                        MaterialTheme.colors.primary
                                    } else {
                                        MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                                    }
                                )
                            }
                        }
                    }
                }

                item {
                    CardsSection(
                        title = "Retos de ${challenge.challengeCategory.title}",
                        cards = (0..10)
                            .toList()
                            .map {
                                object : UICard {
                                    override val id: Int = it
                                    override val name: String = "Recompensa $it"
                                    override val description: String =
                                        "Descripción de la recompensa $it"
                                    override val image: String =
                                        "https://picsum.photos/id/${it}/200/300"
                                }
                            },
                    )
                }
            }
        }
    }
}