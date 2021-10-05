package com.linc.data.dto.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class AccountResponseDTO(
    val id: String,
    val email: String,
    val password: String,
    val createdTimestamp: Long,
    val userId: String,
)