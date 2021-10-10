package com.linc.data.dto.request.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNameDTO(
    val name: String
)