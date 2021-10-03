package com.linc.entity

import java.util.*

data class UserEntity(
    val id: UUID,
    val status: String?,
    val publicProfile: Boolean,
    val avatarId: UUID?,
)