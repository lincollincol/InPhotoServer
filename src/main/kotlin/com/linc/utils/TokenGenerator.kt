package com.linc.utils

import java.security.SecureRandom
import java.util.*

class TokenGenerator(
    private val secureRandom: SecureRandom,
    private val base64UrlEncoder: Base64.Encoder
) {

    fun generateToken() : String {
        val bytes = ByteArray(24)
        secureRandom.nextBytes(bytes)
        return base64UrlEncoder.encodeToString(bytes)
    }

}