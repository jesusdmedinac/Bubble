package com.jesusdmedinac.bubble.data

import data.local.NetworkAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect

class NetworkAPIImpl : NetworkAPI {
    val isConnected = MutableStateFlow(false)
    val upstreamBandWidthKbps = MutableStateFlow(0)
    val downstreamBandWidthKbps = MutableStateFlow(0)

    override suspend fun isConnected(onChange: (Boolean) -> Unit) {
        isConnected.collect {
            onChange(it)
        }
    }

    override suspend fun upstreamBandWidthKbps(onChange: (Int) -> Unit) {
        upstreamBandWidthKbps.collect {
            onChange(it)
        }
    }

    override suspend fun downstreamBandWidthKbps(onChange: (Int) -> Unit) {
        downstreamBandWidthKbps.collect {
            onChange(it)
        }
    }
}