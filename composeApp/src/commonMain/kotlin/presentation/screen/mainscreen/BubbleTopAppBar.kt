package presentation.screen.mainscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.koin.koinNavigatorScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import presentation.screen.tab.bubble.BubbleTabActions
import presentation.screen.tab.bubble.BubbleTabTitle
import presentation.screenmodel.BubbleTabScreenModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BubbleTopAppBar() {
    Column {
        TopAppBar(
            title = {},
            actions = {
                BubbleTabActions()
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = MaterialTheme.colorScheme.onSurface,
                navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.padding(8.dp),
        )
        Divider()
    }
}