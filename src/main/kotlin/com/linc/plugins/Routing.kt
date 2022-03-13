package com.linc.plugins

import com.linc.routes.auth
import com.linc.routes.photos
import com.linc.routes.posts
import com.linc.routes.users
import com.linc.utils.Constants.AUTH_CONFIG
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.content.*
import io.ktor.routing.*

fun Application.configureRouting() {
    routing {
        auth()
        photos()

        posts()

        authenticate(AUTH_CONFIG) {
            users()
        }

        // Static plugin. Try to access `/static/index.html`
        static("/static") {
            resources("static")
        }
    }
}
