package com.linc.data.network.dto.request.users

import kotlinx.serialization.Serializable

@Serializable
data class UpdateNameDTO(
    val name: String
)