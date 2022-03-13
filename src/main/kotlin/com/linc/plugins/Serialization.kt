package com.linc.plugins

import io.ktor.serialization.*
import io.ktor.features.*
import io.ktor.application.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson()
    }
    /*install(ContentNegotiation) {
        json()
    }

    routing {
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
    }*/
}
