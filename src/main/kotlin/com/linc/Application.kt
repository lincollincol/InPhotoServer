package com.linc

import com.linc.plugins.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8885, host = "0.0.0.0") {
//    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
        configureDi()
        configureDatabase()

        configureAuth()

        configureRouting()
        configureSerialization()
        configureMonitoring()

        // TODO: 19.04.22 uncomment when release or new version
//        synchronizeLocalData()
    }.start(wait = true)
}
