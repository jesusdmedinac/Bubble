package com.jesusdmedinac.bubble.data

import data.local.NetworkAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkAPIImpl : NetworkAPI {
    val isConnected = MutableStateFlow(false)
    val upstreamBandWidthKbps = MutableStateFlow(0)
    val downstreamBandWidthKbps = MutableStateFlow(0)

    override fun isConnected(): Flow<Boolean> = isConnected
    override fun upstreamBandWidthKbps(): Flow<Int> = upstreamBandWidthKbps

    override fun downstreamBandWidthKbps(): Flow<Int> = downstreamBandWidthKbps
}