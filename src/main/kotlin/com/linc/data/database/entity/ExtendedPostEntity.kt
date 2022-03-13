package com.linc.data.database.entity

data class ExtendedPostEntity(
    val id: String,
    val createdTimestamp: Long,
    val description: String,
    val contentUrl: String,
    val username: String,
    val userAvatarUrl: String?,
    val userId: String,
    val tagsCount: Int
)