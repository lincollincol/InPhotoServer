package com.linc.model

import com.linc.data.database.entity.user.Gender

data class UserModel(
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