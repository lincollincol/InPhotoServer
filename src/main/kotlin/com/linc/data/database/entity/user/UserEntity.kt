package com.linc.data.database.entity.user

data class UserEntity(
    val id: String,
    val name: String,
    val email: String,
    val status: String?,
    val publicProfile: Boolean,
    val avatarUrl: String?,
)