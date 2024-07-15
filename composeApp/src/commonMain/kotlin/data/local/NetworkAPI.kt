package data.local

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

enum class ConnectionState {
    Idle,
    Connected,
    Disconnected
}

interface NetworkAPI {
    suspend fun isConnected(onChange: (Boolean) -> Unit)
    suspend fun upstreamBandWidthKbps(onChange: (Int) -> Unit)
    suspend fun downstreamBandWidthKbps(onChange: (Int) -> Unit)

    companion object {
        val Default = object : NetworkAPI {
            override suspend fun isConnected(onChange: (Boolean) -> Unit) {
                TODO("isConnected on NetworkAPI is not yet implemented")
            }

            override suspend fun upstreamBandWidthKbps(onChange: (Int) -> Unit) {
                TODO("upstreamBandWidthKbps on NetworkAPI is not yet implemented")
            }

            override suspend fun downstreamBandWidthKbps(onChange: (Int) -> Unit) {
                TODO("downstreamBandWidthKbps on NetworkAPI is not yet implemented")
            }
        }
    }
}