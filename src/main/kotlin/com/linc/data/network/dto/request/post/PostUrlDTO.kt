package com.linc.data.network.dto.request.post

data class PostUrlDTO(
    val url: String,
    val description: String,
    val tags: List<String>
)