package com.linc.data.database.entity

data class PostEntity(
    val id: String,
    val createdTimestamp: Long,
    val description: String,
    val contentUrl: String,
    val userId: String
)