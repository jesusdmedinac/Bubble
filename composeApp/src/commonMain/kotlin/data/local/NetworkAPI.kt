package data.local

import kotlinx.coroutines.flow.Flow

interface NetworkAPI {
    fun isConnected(): Flow<Boolean>
    fun upstreamBandWidthKbps(): Flow<Int>
    fun downstreamBandWidthKbps(): Flow<Int>

    companion object {
        val Default = object : NetworkAPI {
            override fun isConnected(): Flow<Boolean> {
                TODO("isConnected on NetworkAPI is not yet implemented")
            }

            override fun upstreamBandWidthKbps(): Flow<Int> {
                TODO("upstreamBandWidthKbps on NetworkAPI is not yet implemented")
            }

            override fun downstreamBandWidthKbps(): Flow<Int> {
                TODO("downstreamBandWidthKbps on NetworkAPI is not yet implemented")
            }
        }
    }
}