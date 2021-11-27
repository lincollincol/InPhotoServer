package com.linc.data.network.dto.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpDTO(
    val email: String,
    val username: String,
    val password: String
)
