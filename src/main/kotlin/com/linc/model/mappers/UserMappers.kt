package com.linc.model.mappers

import com.linc.data.database.entity.user.UserEntity
import com.linc.data.database.entity.user.UserExtendedEntity
import com.linc.model.UserModel
import com.linc.utils.extensions.EMPTY


fun UserExtendedEntity.toModel(
    followersCount: Int,
    followingCount: Int
) = UserModel(
    id = id,
    name = name,
    email = email,
    status = status,
    gender = gender,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    headerUrl = headerUrl,
    accessToken = accessToken,
    followersCount = followersCount,
    followingCount = followingCount
)

fun UserEntity.toModel(
    followersCount: Int,
    followingCount: Int
) = UserModel(
    id = id,
    name = name,
    email = email,
    status = status,
    gender = gender,
    publicProfile = publicProfile,
    avatarUrl = avatarUrl,
    headerUrl = headerUrl,
    followersCount = followersCount,
    followingCount = followingCount,
    accessToken = String.EMPTY
)