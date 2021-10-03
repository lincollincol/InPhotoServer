package com.linc.data.dto.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDTO(
    val email: String,
    val password: String
)
