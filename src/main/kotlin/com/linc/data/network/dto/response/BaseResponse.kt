package com.linc.data.network.dto.response

data class BaseResponse<T>(
    val status: String,
    val failed: Boolean,
    val body: T?
)