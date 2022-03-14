package com.linc.data.network.dto.request.post

data class LikePostDTO(
    val userId: String,
    val postId: String,
    val like: Boolean
)