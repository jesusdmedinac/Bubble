package com.jesusdmedinac.bubble

import com.jesusdmedinac.bubble.presentation.routing.routing
import io.ktor.server.application.Application


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    serverEngine()
    routing()
}