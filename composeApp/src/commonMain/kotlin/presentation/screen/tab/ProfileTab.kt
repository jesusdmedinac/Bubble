package presentation.screen.tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_chat_bubble
import bubble.composeapp.generated.resources.tab_title_profile
import cafe.adriel.voyager.koin.getNavigatorScreenModel
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import data.formattedDuration
import data.today
import di.LocalAppNavigator
import di.LocalUsageAPI
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import presentation.screenmodel.ProfileTabScreenModel
import presentation.screenmodel.ProfileTabState
import kotlin.math.max
import kotlin.math.roundToInt

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
        val tabNavigator = LocalTabNavigator.current
        val appNavigator = LocalAppNavigator.currentOrThrow
        val usageAPI = LocalUsageAPI.current
        val screenModel = appNavigator.getNavigatorScreenModel<ProfileTabScreenModel>()
        val state by screenModel.container.stateFlow.collectAsState()
        LaunchedEffect(Unit) {
            screenModel.loadUsageStats()
        }
        var completedChallengesIsVisible by remember { mutableStateOf(false) }
        val animatedChallengesIconRotation: Float by animateFloatAsState(if (completedChallengesIsVisible) 180f else 0f)
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (state.isUserLoggedIn) {
                item {
                    UserProfileCard()
                }
            }

            item {
                if (state.hasUsagePermission) {
                    UsageStatsGraph(state)
                } else {
                    UsagePermissionRequest {
                        usageAPI.requestUsagePermission()
                    }
                }
            }

            if (state.savedChallenges.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            """
                                Veo que aún no has guardado ningún reto. ¿Te gustaría que te sugiera algunos retos personalizados para empezar? 😊
                            """.trimIndent()
                        )
                        Button(onClick = {
                            tabNavigator.current = BubbleTab
                        }) {
                            Text("Platicar con Bubble")
                            Icon(
                                painterResource(Res.drawable.ic_chat_bubble),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            } else {
                item {
                    Text(
                        "Desafíos guardados",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
            }

            items(state.savedChallenges) {
                ChallengeSwipeableItem()
            }

            if (state.completedChallenges.isNotEmpty()) {
                item {
                    Row(
                        modifier = Modifier
                            .clickable {
                                completedChallengesIsVisible = !completedChallengesIsVisible
                            }
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            "Desafíos completados",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 16.dp)
                                .rotate(animatedChallengesIconRotation),
                        )
                    }
                }
            }

            items(state.completedChallenges) {
                AnimatedVisibility(completedChallengesIsVisible) {
                    ChallengeSwipeableItem()
                }
            }
        }
    }

    @Composable
    private fun UsagePermissionRequest(
        onPermissionRequestClick: () -> Unit,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                """
                    ¡Ups! parece que no tengo permiso de revisar tu tiempo en pantalla. Puedes activar esta función en "Acceso al uso"
                """.trimIndent()
            )
            Button(onClick = {
                onPermissionRequestClick()
            }) {
                Text("Acceso al uso")
            }
        }
    }

    @Composable
    private fun UsageStatsGraph(
        state: ProfileTabState,
    ) {
        Column {
            Text(
                text = "Tiempo en pantalla",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = state.formattedTodayDate(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = state.formattedAverageTimeInForeground(),
                        style = MaterialTheme.typography.titleLarge,
                    )
                    val pathEffect =
                        PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    val textMeasurer = rememberTextMeasurer()
                    val typography = MaterialTheme.typography
                    val colorScheme = MaterialTheme.colorScheme
                    val dailyUsageStats = state.dailyUsageStats
                    val maxTimeInForeground = dailyUsageStats
                        .maxOfOrNull {
                            it.usageStats.maxOfOrNull { stats -> stats.totalTimeInForeground } ?: 0
                        }
                        ?: 0
                    val averageTimeInForeground = state.averageTimeInForeground()
                    val timeRanges = listOf(
                        UITimeRange((0..(30 * 1000)), 3),
                        UITimeRange(((30 * 1000)..(60 * 1000)), 3),
                        UITimeRange(((60 * 1000)..(2 * 60 * 1000)), 2),
                        UITimeRange((2 * 60 * 1000..10 * 60 * 1000), 2),
                        UITimeRange((10 * 60 * 1000..30 * 60 * 1000), 3),
                        UITimeRange((30 * 60 * 1000..60 * 60 * 1000), 3),
                        UITimeRange((60 * 60 * 1000..3 * 60 * 60 * 1000), 3),
                        UITimeRange((3 * 60 * 60 * 1000..10 * 60 * 60 * 1000), 2),
                        UITimeRange((10 * 60 * 60 * 1000..24 * 60 * 60 * 1000), 3),
                    )
                    val timeRange = timeRanges
                        .first { maxTimeInForeground in it.range }
                    val range = timeRange.range
                    val divisions = timeRange.divisions
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
                                val maxBarHeight =
                                    (size.height / (divisions + 2)) * divisions
                                val averageLineYPosition = maxBarHeight +
                                        (size.height / (divisions + 2)) -
                                        (maxBarHeight / range.last.toLong() * averageTimeInForeground)

                                for (index in divisions downTo 0) {
                                    val topYOffset = size.height -
                                            (size.height / (divisions + 2)) -
                                            ((size.height / (divisions + 2)) * index)
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(
                                            0f,
                                            topYOffset
                                        ),
                                        end = Offset(
                                            size.width - (size.width / 8),
                                            topYOffset
                                        ),
                                        strokeWidth = 2f
                                    )
                                    val label = (range.last.toLong() / divisions * index)
                                        .formattedDuration(
                                            shortenFormat = true,
                                        )
                                        .ifEmpty {
                                            val last = (range.last.toLong() / divisions)
                                                .formattedDuration(shortenFormat = true)
                                                .last()
                                            "0$last"
                                        }
                                    if (index == 0 || index == divisions) {
                                        drawText(
                                            textMeasurer = textMeasurer,
                                            text = label,
                                            topLeft = Offset(
                                                size.width - (size.width / 8) + 2.dp.toPx(),
                                                topYOffset - 8.dp.toPx()
                                            ),
                                            style = typography.bodySmall
                                        )
                                    }
                                }
                                listOf(
                                    "L",
                                    "M",
                                    "M",
                                    "J",
                                    "V",
                                    "S",
                                    "D",
                                    ""
                                ).forEachIndexed { index, dayOfTheWeekFirstLetter ->
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(
                                            (size.width / 8) * index,
                                            size.height / 7
                                        ),
                                        end = Offset(
                                            (size.width / 8) * index,
                                            size.height
                                        ),
                                        strokeWidth = 2f,
                                        pathEffect = pathEffect
                                    )
                                    if (dayOfTheWeekFirstLetter != "") {
                                        drawText(
                                            textMeasurer = textMeasurer,
                                            text = dayOfTheWeekFirstLetter,
                                            topLeft = Offset(
                                                ((size.width / 8) * index) + 2.dp.toPx(),
                                                size.height - 16.dp.toPx()
                                            ),
                                            style = typography.bodySmall.copy(
                                                color = if (index == today().dayOfWeek.ordinal) Color.Black
                                                else Color.LightGray,
                                            )
                                        )
                                    }
                                }

                                dailyUsageStats.forEachIndexed { index, uiDailyUsageStats ->
                                    val barHeight = max(
                                        maxBarHeight /
                                                range.last.toLong() *
                                                uiDailyUsageStats
                                                    .usageStats
                                                    .sumOf { it.totalTimeInForeground },
                                        2.dp.toPx()
                                    )
                                    drawRoundRect(
                                        color = colorScheme.primary,
                                        topLeft = Offset(
                                            (size.width / 8) * index,
                                            (size.height / (divisions + 2)) + maxBarHeight - barHeight
                                        ),
                                        size = Size(
                                            size.width / 8 - 16.dp.toPx(),
                                            barHeight
                                        ),
                                        cornerRadius = CornerRadius(1.dp.toPx(), 1.dp.toPx())
                                    )
                                }
                                drawLine(
                                    color = Color(0xFF00C853),
                                    start = Offset(0f, averageLineYPosition),
                                    end = Offset(
                                        size.width - (size.width / 8),
                                        averageLineYPosition
                                    ),
                                    strokeWidth = 2f,
                                    pathEffect = pathEffect
                                )
                                drawText(
                                    textMeasurer = textMeasurer,
                                    text = "prom.",
                                    topLeft = Offset(
                                        size.width - (size.width / 8),
                                        averageLineYPosition - 8.dp.toPx()
                                    ),
                                    style = typography.bodySmall.copy(
                                        color = Color(0xFF00C853),
                                    )
                                )
                            }
                    ) {
                    }
                }
            }
        }
    }

    @Composable
    private fun UserProfileCard() {
        ListItem(
            leadingContent = {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {}
            },
            headlineContent = {
                Text(
                    "Nombre del usuario",
                )
            },
            supportingContent = {
                Text(
                    "Correo electrónico",
                )
            }
        )
    }

    @Composable
    private fun ChallengeSwipeableItem() {
        Swipeable(
            onClink = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(88.dp),
            content = {
                Row(
                    modifier = it,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val primary = MaterialTheme.colorScheme.primary
                    Box(
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(72.dp)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(primary)
                    ) {

                    }
                    Column {
                        Text(
                            text = "Challenge",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                        Text(
                            text = "Status",
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }
            },
            leadActions = {
                TextButton(
                    onClick = {},
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Completar")
                }
            },
            trailingActions = {
                TextButton(
                    onClick = {},
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.onError,
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

enum class DragAnchors {
    Start,
    End,
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Swipeable(
    onClink: () -> Unit,
    content: @Composable (Modifier) -> Unit,
    leadActions: @Composable RowScope.() -> Unit = {},
    trailingActions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var width by remember { mutableStateOf(1) }
    //val swipeableState = rememberSwipeableState(width)
    val anchoredDraggableState = remember {
        AnchoredDraggableState(
            // 2
            initialValue = DragAnchors.Start,
            // 3
            positionalThreshold = { distance: Float -> distance * 0.5f },
            // 4
            velocityThreshold = { with(density) { 100.dp.toPx() } },
            // 5
            animationSpec = tween(),
        ).apply {
            // 6
            updateAnchors(
                // 7
                DraggableAnchors {
                    DragAnchors.Start at 0f
                    DragAnchors.End at 400f
                }
            )
        }
    }
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .clickable {
                coroutineScope.launch {
                    anchoredDraggableState.animateTo(DragAnchors.End)
                }
                onClink()
            }
            .onGloballyPositioned {
                width = it.size.width
            }
            .width(width.dp)
            .offset {
                IntOffset(
                    // 2
                    x = anchoredDraggableState.requireOffset().roundToInt(),
                    y = 0,
                )
            }
            .anchoredDraggable(anchoredDraggableState, Orientation.Horizontal)
        /*.swipeable(
            state = swipeableState,
            anchors = mapOf(0f - (width / 3) to 0, 0f to 1, (width / 3f) to 2),
            thresholds = { _, _ -> FractionalThreshold(0.3f) },
            orientation = Orientation.Horizontal
        )*/
    ) {
        Row(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.3f)
                .align(Alignment.CenterStart)
        ) {
            leadActions()
        }
        Row(
            Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.3f)
                .align(Alignment.CenterEnd)
        ) {
            trailingActions()
        }
        content(
            Modifier
                .offset { IntOffset(anchoredDraggableState.offset.roundToInt(), 0) }
                .width(width.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(8.dp)
        )
    }
}

data class UITimeRange(
    val range: IntRange,
    val divisions: Int,
)