package com.linc.model

data class StoryModel(
    val id: String,
    val contentUrl: String,
    val duration: Long,
    val createdTimestamp: Long,
    val expiresTimestamp: Long
)