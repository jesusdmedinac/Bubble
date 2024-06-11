package presentation.screen.tab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.tab_title_profile
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import presentation.screen.tab.bubble.BubbleTabActions
import presentation.screen.tab.bubble.BubbleTabTitle
import kotlin.math.roundToInt

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
            var completedChallengesIsVisible by remember { mutableStateOf(false) }
            val animatedChallengesIconRotation: Float by animateFloatAsState(if (completedChallengesIsVisible) 180f else 0f)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                item {
                    Text(
                        "Desafíos guardados",
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }

                items(5) {
                    ChallengeSwipeableItem()
                }

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
                            style = MaterialTheme.typography.h6,
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

                items(5) {
                    AnimatedVisibility(completedChallengesIsVisible) {
                        ChallengeSwipeableItem()
                    }
                }
            }
        }
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
                    val primary = MaterialTheme.colors.primary
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
                            style = MaterialTheme.typography.h6,
                        )
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.subtitle1,
                        )
                        Text(
                            text = "Status",
                            style = MaterialTheme.typography.subtitle2,
                        )
                    }
                }
            },
            leadActions = {
                TextButton(
                    onClick = {},
                    modifier = Modifier.fillMaxSize(),
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colors.onSecondary,
                        backgroundColor = MaterialTheme.colors.secondary
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
                        contentColor = MaterialTheme.colors.onError,
                        backgroundColor = MaterialTheme.colors.error
                    )
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Swipeable(
    onClink: () -> Unit,
    content: @Composable (Modifier) -> Unit,
    leadActions: @Composable RowScope.() -> Unit = {},
    trailingActions: @Composable RowScope.() -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var width by remember { mutableStateOf(1) }
    val swipeableState = rememberSwipeableState(width)
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = modifier
            .clickable {
                coroutineScope.launch {
                    swipeableState.animateTo(1)
                }
                onClink()
            }
            .onGloballyPositioned {
                width = it.size.width
            }
            .width(width.dp)
            .swipeable(
                state = swipeableState,
                anchors = mapOf(0f - (width / 3) to 0, 0f to 1, (width / 3f) to 2),
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
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
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .width(width.dp)
                .background(MaterialTheme.colors.background)
                .padding(8.dp)
        )
    }
}