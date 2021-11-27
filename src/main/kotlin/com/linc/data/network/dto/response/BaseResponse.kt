package com.linc.data.network.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean,
    val error: String?,
    val body: T?
)