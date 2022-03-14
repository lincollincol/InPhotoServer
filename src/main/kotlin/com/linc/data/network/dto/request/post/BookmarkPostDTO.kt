package com.linc.data.network.dto.request.post

data class BookmarkPostDTO(
    val userId: String,
    val postId: String,
    val bookmark: Boolean
)