import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import io.kamel.core.Resource
import io.ktor.client.HttpClient

expect fun getHttpClient(): HttpClient

@Composable
expect fun asyncPainterResourceKamel(urlString: String): Resource<Painter>