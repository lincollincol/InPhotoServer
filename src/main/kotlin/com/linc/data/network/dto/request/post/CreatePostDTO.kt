package com.linc.data.network.dto.request.post

data class CreatePostDTO(
    val userId: String,
    val description: String,
    val tags: List<String>,
)