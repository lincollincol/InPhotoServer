package com.linc.entity

import java.util.*

data class ContentEntity(
    val id: UUID,
    val data: ByteArray,
    val extension: String
)