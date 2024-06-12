package presentation.screen.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import generativeui.ColumnLayout
import generativeui.ComposeAsyncImageNode
import generativeui.ComposeNode
import generativeui.ComposeTextNode
import generativeui.ContentScaleImageAttribute
import generativeui.Node
import generativeui.PaddingNodeModifier
import generativeui.ShapeNode
import generativeui.SizeNodeModifier
import generativeui.TextNode
import generativeui.TextNodeTypography
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.http.Url
import org.jetbrains.compose.resources.stringResource
import presentation.screen.mainscreen.BubbleTopAppBar
import presentation.screen.tab.home.ChallengesCategories
import presentation.screen.tab.home.ChallengesHomeSection
import presentation.screen.tab.home.DonorsShorts

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
                ChallengesHomeSection(
                    title = "Retos guardados",
                )
            }
            item {
                ChallengesCategories()
            }
            item {
                DonorsShorts()
            }
            item {
                ChallengesHomeSection(
                    title = "Recomendados por Starbucks",
                )
            }
        }
    }
}
