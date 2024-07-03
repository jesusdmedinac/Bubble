package presentation.screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_close
import cafe.adriel.voyager.core.screen.Screen
import org.jetbrains.compose.resources.painterResource

class PaywallScreen : Screen {
    @Composable
    override fun Content() {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_close),
                                contentDescription = null,
                            )
                        }
                    },
                    backgroundColor = Color.Transparent,
                    elevation = 0.dp,
                )
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .height(256.dp)
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Text("Awesome Image")
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(32.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 32.dp,
                                        topEnd = 32.dp,
                                        bottomStart = 0.dp,
                                        bottomEnd = 0.dp
                                    )
                                )
                                .background(MaterialTheme.colors.background)
                        ) { }
                    }
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        // Tittle to invite people to join Bubble Premium
                        Text(
                            "Únete a Bubble Premium",
                            style = MaterialTheme.typography.h4,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "¡Aprovecha la promoción exclusiva para nuevos miembros!",
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        var isChecked by remember { mutableStateOf(false) }
                        val animatedSpacerWidthFraction by animateFloatAsState(if (isChecked) 0.5f else 0f)
                        val primary = MaterialTheme.colors.primary
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .height(56.dp)
                                .clip(CircleShape)
                                .background(color = MaterialTheme.colors.secondary)
                                .clickable {
                                    isChecked = !isChecked
                                }
                        ) {
                            val localDensity = LocalDensity.current
                            Canvas(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(40.dp))
                            ) {
                                drawRoundRect(
                                    color = primary,
                                    topLeft = Offset.Zero.copy(x = size.width * animatedSpacerWidthFraction),
                                    size = size.copy(size.width * 0.5f),
                                    cornerRadius = with(localDensity) { CornerRadius(40.dp.toPx()) }
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .fillMaxWidth(0.5f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(40.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "Premium",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isChecked) MaterialTheme.colors.onPrimary
                                    else MaterialTheme.colors.onSecondary
                                )
                                Text(
                                    "50% OFF",
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .background(color = MaterialTheme.colors.primaryVariant)
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    color = MaterialTheme.colors.onPrimary
                                )
                            }

                            Row(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .fillMaxWidth(0.5f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(40.dp)),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "Plan actual",
                                    fontWeight = FontWeight.Bold,
                                    color = if (isChecked) MaterialTheme.colors.onSecondary
                                    else MaterialTheme.colors.onPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}