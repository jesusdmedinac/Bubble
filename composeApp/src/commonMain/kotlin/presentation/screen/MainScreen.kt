package presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_help
import bubble.composeapp.generated.resources.ic_premium
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.core.screen.uniqueScreenKey
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import di.LocalAppNavigator
import org.jetbrains.compose.resources.painterResource
import presentation.screen.mainscreen.BubbleTopAppBar
import presentation.screen.tab.BubbleTab
import presentation.screen.tab.ProfileTab

object MainScreen : Screen {
    override val key: ScreenKey = uniqueScreenKey

    @Composable
    override fun Content() {
        TabNavigator(BubbleTab) { tabNavigator: TabNavigator ->
            Scaffold(
                topBar = {
                    BubbleTopAppBar()
                },
                bottomBar = {
                    NavigationBar {
                        //TabNavigationItem(HomeTab)
                        TabNavigationItem(BubbleTab)
                        TabNavigationItem(ProfileTab)
                        PaywallTabNavigationItem()
                        FeedbackTabNavigationItem()
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
    private fun RowScope.PaywallTabNavigationItem() {
        val appNavigator = LocalAppNavigator.currentOrThrow
        NavigationBarItem(
            selected = false,
            onClick = { appNavigator.push(PaywallScreen) },
            icon = {
                Icon(
                    painterResource(Res.drawable.ic_premium),
                    contentDescription = null
                )
            },
        )
    }

    @Composable
    private fun RowScope.FeedbackTabNavigationItem() {
        val uriHandler = LocalUriHandler.current
        NavigationBarItem(
            selected = false,
            onClick = {
                uriHandler.openUri("https://tally.so/r/w2kMdV")
            },
            icon = {
                Icon(
                    painterResource(Res.drawable.ic_help),
                    contentDescription = null
                )
            },
        )
    }

    @Composable
    private fun RowScope.TabNavigationItem(tab: Tab) {
        val tabNavigator = LocalTabNavigator.current

        NavigationBarItem(
            selected = tabNavigator.current == tab,
            onClick = { tabNavigator.current = tab },
            icon = {
                Icon(
                    painter = tab.options.icon ?: return@NavigationBarItem,
                    contentDescription = tab.options.title,
                )
            },
        )
    }
}