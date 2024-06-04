import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import io.kamel.core.Resource
import io.kamel.core.utils.File
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual fun getHttpClient(): HttpClient = HttpClient(Darwin) {
    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}

@Composable
actual fun asyncPainterResourceKamel(urlString: String): Resource<Painter> =
    asyncPainterResource(data = File(urlString))