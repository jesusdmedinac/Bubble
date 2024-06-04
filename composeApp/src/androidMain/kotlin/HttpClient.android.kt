import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import io.kamel.core.Resource
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.http.Url
import java.net.InetSocketAddress
import java.net.Proxy

actual fun getHttpClient(): HttpClient = HttpClient(Android) {
    engine {
        // this: AndroidEngineConfig
        connectTimeout = 100_000
        socketTimeout = 100_000
    }
}

@Composable
actual fun asyncPainterResourceKamel(urlString: String): Resource<Painter> =
    asyncPainterResource(data = Url(urlString))