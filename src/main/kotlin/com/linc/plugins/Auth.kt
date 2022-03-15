package com.linc.plugins

import com.linc.data.repository.UsersRepository
import com.linc.utils.Constants.AUTH_CONFIG
import com.linc.utils.JWTUtils
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import org.koin.ktor.ext.inject

fun Application.configureAuth() {

    val jwtUtils: JWTUtils by inject()
    val usersRepository: UsersRepository by inject()

    install(Authentication) {
        jwt(AUTH_CONFIG) {
            verifier(jwtUtils.verifier)
            realm = "ktor.io"
            validate { credentials ->
                val id = credentials.payload
                    .getClaim("id")
                    .asString()
                try {
                    usersRepository.getUserById(id)
                    JWTPrincipal(credentials.payload)
                } catch (e: Exception) {
                    null
                }
            }
        }
    }
}

