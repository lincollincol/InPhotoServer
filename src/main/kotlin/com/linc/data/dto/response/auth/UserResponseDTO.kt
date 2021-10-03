package com.linc.data.dto.response.auth

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserResponseDTO(
    val id: String,
    val status: String?,
    val publicProfile: Boolean,
    val avatarId: String?,
)