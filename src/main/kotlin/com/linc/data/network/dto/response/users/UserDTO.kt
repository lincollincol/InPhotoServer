package com.linc.data.network.dto.response.users

import com.linc.data.database.entity.user.Gender
@Deprecated(level = DeprecationLevel.ERROR, message = "Replace with UserModel")
data class UserDTO(
    val id: String,
    val name: String,
    val email: String,
    val status: String?,
    val gender: Gender,
    val publicProfile: Boolean,
    val avatarUrl: String,
    val headerUrl: String,
    val accessToken: String,
    val followersCount: Int,
    val followingCount: Int
)