package com.linc.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class JWTUtils {

    companion object {
        private const val secret = "zAP5MBA4B4Ijz0MZaS48"
        private const val issuer = "ktor.io"
    }

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    /**
     * Produce a token for this combination of User and Account
     */
    fun createToken(id: String): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(issuer)
        .withClaim("id", id)
        .sign(algorithm)

}