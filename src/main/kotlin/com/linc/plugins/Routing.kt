package com.linc.plugins

import com.linc.routes.*
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        auth()
        photos()

        posts()

        testRoute()
        users()
//        authenticate(AUTH_CONFIG) {
//        }

        content()
        chats()
        tags()
        stories()
        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
