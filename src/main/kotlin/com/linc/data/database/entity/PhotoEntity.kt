package com.linc.data.database.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PhotoEntity(
    @SerialName("assa")
    val uid: String,
    val name: String,
    val url: String
) {
    constructor(name: String, url: String) : this(UUID.randomUUID().toString(), name, url)
}