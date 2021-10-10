package com.linc.data.dto.response.auth

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class UserDTO(
    val id: String,
    val name: String?,
    val email: String,
    val status: String?,
    val publicProfile: Boolean,
    val accessToken: String,
    val avatarId: String?,
)