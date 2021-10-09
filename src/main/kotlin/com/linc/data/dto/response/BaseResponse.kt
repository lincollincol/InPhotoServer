package com.linc.data.dto.response

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val success: Boolean = true,
    val body: T?
)