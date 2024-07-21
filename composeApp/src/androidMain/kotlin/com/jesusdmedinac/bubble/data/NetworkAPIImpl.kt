package com.jesusdmedinac.bubble.data

import data.local.ConnectionState
import data.local.NetworkAPI
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkAPIImpl : NetworkAPI {
    val connectionState = MutableStateFlow(ConnectionState.Idle)
    val upstreamBandWidthKbps = MutableStateFlow(0)
    val downstreamBandWidthKbps = MutableStateFlow(0)

    override suspend fun onConnectionStateChange(onChange: (ConnectionState) -> Unit) {
        connectionState.collect {
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