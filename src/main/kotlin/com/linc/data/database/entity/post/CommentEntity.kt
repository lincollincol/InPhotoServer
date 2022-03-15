package com.linc.data.database.entity.post

data class CommentEntity(
    val id: String,
    val text: String,
    val username: String,
    val userAvatarUrl: String?
)