package com.linc.data.dto.request.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserNameRequestDTO(
    @SerialName("uuid")
    val id: String,
    val name: String
)