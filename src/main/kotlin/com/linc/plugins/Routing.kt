package com.linc.plugins

import com.linc.routes.*
import com.linc.utils.Constants.AUTH_CONFIG
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.content.*
import io.ktor.http.content.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.request.*

fun Application.configureRouting() {
    routing {
        auth()
        photos()

//        publicTest()

        authenticate(AUTH_CONFIG) {
//            privateTest()
            users()
        }

        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
