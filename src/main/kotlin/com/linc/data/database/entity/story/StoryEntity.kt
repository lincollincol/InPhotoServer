package com.linc.data.database.entity.story

data class StoryEntity(
    val id: String,
    val contentUrl: String,
    val duration: Long,
    val createdTimestamp: Long,
    val expiresTimestamp: Long
)