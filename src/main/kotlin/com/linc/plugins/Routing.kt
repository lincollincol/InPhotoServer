package com.linc.plugins

import com.linc.routes.auth
import com.linc.routes.photos
import com.linc.routes.users
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

        authenticate(AUTH_CONFIG) {
            users()
            photos()
        }

        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
