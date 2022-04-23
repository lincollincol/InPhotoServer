package com.linc.data.network.mapper

import com.linc.data.database.entity.user.UserEntity
import com.linc.data.database.entity.user.UserExtendedEntity
import com.linc.data.network.dto.response.users.UserDTO

fun UserDTO.toUserEntity() = UserEntity(
    id = id,
    name = name,
    email = email,
    gender = gender,
    status = status,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    headerUrl = headerUrl
)

fun UserDTO.toUserExtendedEntity() = UserExtendedEntity(
    id = id,
    name = name,
    email = email,
    status = status,
    gender = gender,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    headerUrl = headerUrl,
    accessToken = accessToken
)

fun UserEntity.toUserExtendedEntity(accessToken: String) = UserExtendedEntity(
    id = id,
    name = name,
    email = email,
    status = status,
    gender = gender,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    headerUrl = headerUrl,
    accessToken = accessToken
)

fun UserExtendedEntity.toUserDto() = UserDTO(
    id = id,
    name = name,
    email = email,
    status = status,
    gender = gender,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    headerUrl = headerUrl,
    accessToken = accessToken
)