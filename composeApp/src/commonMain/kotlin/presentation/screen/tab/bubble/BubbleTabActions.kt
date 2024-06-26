package presentation.screen.tab.bubble

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import bubble.composeapp.generated.resources.Res
import bubble.composeapp.generated.resources.ic_help
import bubble.composeapp.generated.resources.ic_premium
import bubble.composeapp.generated.resources.ic_settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BubbleTabActions() {
    Button(
        onClick = {},
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painterResource(Res.drawable.ic_premium),
            contentDescription = null
        )
    }
    Spacer(modifier = Modifier.size(8.dp))
    Button(
        onClick = {},
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painterResource(Res.drawable.ic_settings),
            contentDescription = null
        )
    }
    Spacer(modifier = Modifier.size(8.dp))
    Button(
        onClick = {},
        modifier = Modifier
            .size(48.dp)
            .clip(CircleShape),
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            painterResource(Res.drawable.ic_help),
            contentDescription = null
        )
    }
}
