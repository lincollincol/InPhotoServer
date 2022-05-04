package com.linc.data.network.dto.request.auth

import com.linc.data.database.entity.user.Gender

data class SignUpDTO(
    val email: String,
    val username: String,
    val password: String,
    val gender: Gender
)
