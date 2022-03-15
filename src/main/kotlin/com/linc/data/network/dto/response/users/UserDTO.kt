package com.linc.data.network.dto.response.users

data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
    val status: String?,
    val publicProfile: Boolean,
    val avatarUrl: String?,
    val accessToken: String
)