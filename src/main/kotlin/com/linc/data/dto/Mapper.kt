package com.linc.data.dto

import com.linc.data.dto.response.auth.AccountResponseDTO
import com.linc.data.dto.response.auth.UserResponseDTO
import com.linc.entity.CredentialsEntity
import com.linc.entity.UserEntity
import com.linc.entity.UserExtendedEntity

fun CredentialsEntity.toDTO() = AccountResponseDTO(
    id.toString(),
    email,
    password,
    createdTimestamp.millis,
    userId.toString()
)

fun UserExtendedEntity.toDTO() = UserResponseDTO(
    id.toString(), name, email, status, publicProfile, accessToken, avatarId.toString()
)