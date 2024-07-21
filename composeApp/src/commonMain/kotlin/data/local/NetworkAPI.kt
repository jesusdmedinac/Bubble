package data.local

enum class ConnectionState {
    Idle,
    Connected,
    Disconnected
}

interface NetworkAPI {
    suspend fun onConnectionStateChange(onChange: (ConnectionState) -> Unit)
    suspend fun upstreamBandWidthKbps(onChange: (Int) -> Unit)
    suspend fun downstreamBandWidthKbps(onChange: (Int) -> Unit)

    companion object {
        val Default = object : NetworkAPI {
            override suspend fun onConnectionStateChange(onChange: (ConnectionState) -> Unit) {
                TODO("onConnectionStateChange on NetworkAPI is not yet implemented")
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