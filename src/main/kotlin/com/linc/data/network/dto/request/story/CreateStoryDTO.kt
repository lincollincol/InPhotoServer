package com.linc.data.network.dto.request.story

data class CreateStoryDTO(
    val contentUrl: String,
    val duration: Long,
    val createdTimestamp: Long,
    val expiresTimestamp: Long
)