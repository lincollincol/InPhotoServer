package com.linc.data.entity

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PhotoEntity(
    val uid: String,
    val name: String,
    val url: String
) {
    constructor(name: String, url: String) : this(UUID.randomUUID().toString(), name, url)
}