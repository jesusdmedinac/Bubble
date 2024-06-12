package presentation.screen.tab.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import generativeui.ComposeNode
import generativeui.PaddingNodeModifier
import generativeui.TextNode
import generativeui.TextNodeTypography

@Composable
fun ChallengesCategories() {
    Column {
        ComposeNode(
            TextNode(
                "Descubre retos",
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
                    "Salud y bienestar",
                    "Desafíos Ridículos",
                    "Música y entretenimiento",
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
                            .background(
                                if (category == "Todo") Color.DarkGray
                                else MaterialTheme.colors.primary
                            ),
                    ) {
                        Text(
                            category[0].toString(),
                            color = if (category == "Todo") Color.White
                            else MaterialTheme.colors.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Text(
                        category,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .height(48.dp)
                    )
                }
            }
        }
    }
}