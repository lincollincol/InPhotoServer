package com.linc.data.database.entity

import java.util.*

data class PostTagEntity(
    val id: UUID,
    val tagId: UUID,
    val postId: UUID,
    val tag: String
)