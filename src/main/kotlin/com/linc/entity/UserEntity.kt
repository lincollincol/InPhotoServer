package com.linc.entity

import java.util.*

data class UserEntity(
    val id: UUID,
    val name: String?,
    val status: String?,
    val publicProfile: Boolean,
    val accessToken: String,
    val avatarId: UUID?,
)