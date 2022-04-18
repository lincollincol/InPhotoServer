package com.linc.data.network.dto.request.post

data class UpdatePostDTO(
    val description: String,
    val tags: List<String>
)