package com.linc.data.dto.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class AccountDTO(
    val id: String,
    val email: String,
    val password: String,
    val createdTimestamp: Long,
    val userId: String,
)