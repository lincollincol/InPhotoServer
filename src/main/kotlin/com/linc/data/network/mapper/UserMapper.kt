package com.linc.data.network.mapper

import com.linc.data.network.dto.response.users.UserDTO
import com.linc.data.database.entity.UserEntity
import com.linc.data.database.entity.UserExtendedEntity

fun UserDTO.toUserEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    status = status,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
)

fun UserDTO.toUserExtendedEntity() = UserExtendedEntity(
    id = id,
    name = name,
    email = email,
    status = status,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    accessToken = accessToken
)

fun UserEntity.toUserExtendedEntity(accessToken: String) = UserExtendedEntity(
    id = id,
    name = name,
    email = email,
    status = status,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    accessToken = accessToken
)

fun UserExtendedEntity.toUserDto() = UserDTO(
    id = id,
    name = name,
    email = email,
    status = status,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    accessToken = accessToken
)