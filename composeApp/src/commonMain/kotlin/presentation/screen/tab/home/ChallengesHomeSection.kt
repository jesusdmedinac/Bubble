package presentation.screen.tab.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import generativeui.AsyncImageNode
import generativeui.ClipNodeModifier
import generativeui.ComposeAsyncImageNode
import generativeui.ComposeTextNode
import generativeui.ContentScaleImageAttribute
import generativeui.PaddingNodeModifier
import generativeui.ShapeNode
import generativeui.SizeNodeModifier
import generativeui.TextNode
import generativeui.TextNodeTypography

@Composable
fun ChallengesHomeSection(
    title: String,
) {
    ComposeTextNode(
        TextNode(
            title,
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
                                    SizeNodeModifier(width = 196f, height = 112f),
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