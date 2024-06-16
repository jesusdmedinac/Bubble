package presentation.screen.tab

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.tab_title_home
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import org.jetbrains.compose.resources.stringResource
import presentation.model.ChallengeCategory
import presentation.model.UIChallenge
import presentation.screen.tab.home.ChallengesCategories
import presentation.screen.CardsSection
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
                CardsSection(
                    title = "Retos guardados",
                    cards = (0..10)
                        .toList()
                        .map {
                            UIChallenge(
                                id = it,
                                name = "Reto $it",
                                description = "Descripción del reto $it",
                                image = "https://picsum.photos/id/${it}/200/300",
                                challengeCategory = ChallengeCategory
                                    .entries
                                    .toTypedArray()
                                    .random()
                            )
                        }
                )
            }
            item {
                ChallengesCategories()
            }
            item {
                DonorsShorts()
            }
            item {
                CardsSection(
                    title = "Recomendados por Starbucks",
                    cards = (0..10)
                        .toList()
                        .map {
                            UIChallenge(
                                id = it,
                                name = "Reto $it",
                                description = "Descripción del reto $it",
                                image = "https://picsum.photos/id/${it}/200/300",
                                challengeCategory = ChallengeCategory
                                    .entries
                                    .toTypedArray()
                                    .random()
                            )
                        }
                )
            }
        }
    }
}
