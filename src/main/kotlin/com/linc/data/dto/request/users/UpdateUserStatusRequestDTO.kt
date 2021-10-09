package com.linc.data.dto.request.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserStatusRequestDTO(
    @SerialName("uuid")
    val id: String,
    val status: String
)