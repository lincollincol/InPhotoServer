package com.linc.data.database.entity

import com.google.gson.annotations.SerializedName

import java.util.*

data class PhotoEntity(
    @SerializedName("assa")
    val uid: String,
    val name: String,
    val url: String
) {
    constructor(name: String, url: String) : this(UUID.randomUUID().toString(), name, url)
}