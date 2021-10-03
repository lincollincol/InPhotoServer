package com.linc.data.dto

import com.linc.data.database.table.UsersTable
import com.linc.data.dto.response.auth.AccountResponseDTO
import com.linc.data.dto.response.auth.UserResponseDTO
import com.linc.entity.AccountEntity
import com.linc.entity.UserEntity
import org.jetbrains.exposed.sql.ResultRow

fun AccountEntity.toDTO() = AccountResponseDTO(
    id.toString(),
    email,
    name,
    password,
    createdTimestamp.millis,
    accessToken,
    userId.toString()
)

fun UserEntity.toDTO() = UserResponseDTO(
    id.toString(), status, publicProfile, avatarId.toString()
)