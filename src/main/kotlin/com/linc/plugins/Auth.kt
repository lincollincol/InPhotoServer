package com.linc.plugins

import com.linc.data.repository.UsersRepository
import com.linc.entity.UserEntity
import com.linc.utils.Constants.AUTH_CONFIG
import com.linc.utils.JWTUtils
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.ktor.ext.inject
import kotlin.reflect.KSuspendFunction1

fun Application.configureAuth() {

    val jwtUtils: JWTUtils by inject()

    install(Authentication) {
        jwt(AUTH_CONFIG) {
            verifier(jwtUtils.verifier)
            realm = "ktor.io"
            validate { credentials ->
                val id = credentials.payload
                    .getClaim("id")
                    .asString()

                when {
                    id.isNotEmpty() -> JWTPrincipal(credentials.payload)
                    else -> null
                }

            }
        }
    }
}

