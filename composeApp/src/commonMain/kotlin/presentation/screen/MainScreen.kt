package presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import presentation.screen.mainscreen.BubbleTopAppBar
import presentation.screen.tab.BubbleTab
import presentation.screen.tab.ProfileTab

class MainScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        val bubbleTab = remember { BubbleTab() }
        TabNavigator(bubbleTab) { tabNavigator: TabNavigator ->
            Scaffold(
                topBar = {
                    BubbleTopAppBar()
                },
                bottomBar = {
                    BottomNavigation {
                        //TabNavigationItem(HomeTab)
                        TabNavigationItem(bubbleTab)
                        TabNavigationItem(ProfileTab)
                    }
                },
                content = { padding ->
                    Box(
                        modifier = Modifier.padding(padding)
                    ) {
                        CurrentTab()
                    }
                }
            )
        }
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        BottomNavigationItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = {
                Icon(
                    painter = tab.options.icon ?: return@BottomNavigationItem,
                    contentDescription = tab.options.title,
                )
            },
        )
    }
}