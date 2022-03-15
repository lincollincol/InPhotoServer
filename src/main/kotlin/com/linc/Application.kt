package com.linc

import com.linc.plugins.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
//    embeddedServer(Netty, port = 8885, host = "0.0.0.0") {
//    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {
    embeddedServer(Netty, port = 8885) {
        configureDi()
        configureDatabase()

        configureAuth()

        configureRouting()
        configureSerialization()
        configureMonitoring()

    }.start(wait = true)
}
