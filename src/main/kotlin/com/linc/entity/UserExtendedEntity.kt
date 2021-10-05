package com.linc.entity

import java.util.*

data class UserExtendedEntity(
    val id: UUID,
    val name: String?,
    val email: String,
    val status: String?,
    val publicProfile: Boolean,
    val accessToken: String,
    val avatarId: UUID?,
)