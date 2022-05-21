package com.linc.plugins

import io.ktor.application.*

fun Application.configureAuth() {
//
//    val jwtUtils: JWTUtils by inject()
//    val usersRepository: UsersRepository by inject()
//
//    install(Authentication) {
//        jwt(AUTH_CONFIG) {
//            verifier(jwtUtils.verifier)
//            realm = "ktor.io"
//            validate { credentials ->
//                val id = credentials.payload
//                    .getClaim("id")
//                    .asString()
//                try {
//                    usersRepository.getUserById(id)
//                    JWTPrincipal(credentials.payload)
//                } catch (e: Exception) {
//                    null
//                }
//            }
//        }
//    }
}

