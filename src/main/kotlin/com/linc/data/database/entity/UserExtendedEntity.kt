package com.linc.data.database.entity

import kotlinx.serialization.Serializable

@Serializable
data class UserExtendedEntity(
    val id: String,
    val name: String,
    val email: String,
    val status: String?,
    val publicProfile: Boolean,
    val avatarUrl: String?,
    val accessToken: String
)