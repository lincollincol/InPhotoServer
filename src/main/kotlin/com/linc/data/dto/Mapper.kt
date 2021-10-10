package com.linc.data.dto

import com.linc.data.dto.response.auth.AccountDTO
import com.linc.data.dto.response.auth.UserDTO
import com.linc.entity.CredentialsEntity
import com.linc.entity.UserExtendedEntity

fun CredentialsEntity.toDTO() = AccountDTO(
    id.toString(),
    email,
    password,
    createdTimestamp.millis,
    userId.toString()
)

fun UserExtendedEntity.toDTO() = UserDTO(
    id.toString(), name, email, status, publicProfile, accessToken, avatarId.toString()
)