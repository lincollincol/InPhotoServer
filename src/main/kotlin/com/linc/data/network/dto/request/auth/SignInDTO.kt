package com.linc.data.network.dto.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class SignInDTO(
    val login: String,
    val password: String
)
