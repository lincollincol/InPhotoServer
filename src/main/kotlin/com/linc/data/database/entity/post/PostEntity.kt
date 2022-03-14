package com.linc.data.database.entity.post

data class PostEntity(
    val id: String,
    val createdTimestamp: Long,
    val description: String,
    val contentUrl: String,
    val userId: String
)