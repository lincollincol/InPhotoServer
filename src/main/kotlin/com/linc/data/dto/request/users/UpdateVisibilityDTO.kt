package com.linc.data.dto.request.users

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateVisibilityDTO(
    @SerialName("is_public")
    val isPublic: Boolean
)