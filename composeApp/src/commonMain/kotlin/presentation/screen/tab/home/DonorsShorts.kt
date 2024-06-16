package presentation.screen.tab.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Badge
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import generativeui.AsyncImageNode
import generativeui.ClipNodeModifier
import generativeui.ComposeNode
import generativeui.ComposeTextNode
import generativeui.ContentScaleImageAttribute
import generativeui.PaddingNodeModifier
import generativeui.ShapeNode
import generativeui.SizeNodeModifier
import generativeui.TextNode
import generativeui.TextNodeTypography

@Composable
fun DonorsShorts() {
    ComposeTextNode(
        TextNode(
            "Donadores",
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
                    Box {
                        ComposeNode(
                            AsyncImageNode(
                                url = "https://picsum.photos/id/${it}/200/300",
                                contentDescription = null,
                                nodeModifiers = listOf(
                                    SizeNodeModifier(width = 112f, height = 196f),
                                    ClipNodeModifier(ShapeNode.RoundedCorner(8f)),
                                ),
                                contentScale = ContentScaleImageAttribute.FillHeight
                            )
                        )
                        val brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.White
                            )
                        )
                        Column(
                            modifier = Modifier
                                .size(width = 112.dp, height = 196.dp)
                                .drawBehind {
                                    drawRect(
                                        brush = brush
                                    )
                                }
                        ) {
                            Badge(
                                modifier = Modifier.padding(8.dp)
                                    .clip(CircleShape)
                            ) {
                                Text("Short", modifier = Modifier.padding(2.dp))
                            }
                            Spacer(
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                "Donador $it",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
        item {
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}