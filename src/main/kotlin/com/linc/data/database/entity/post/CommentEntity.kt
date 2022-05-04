package com.linc.data.database.entity.post

data class CommentEntity(
    val id: String,
    val comment: String,
    val createdTimestamp: Long,
    val userId: String,
    val username: String,
    val userAvatarUrl: String
)