package com.linc.data.network

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.linc.utils.extensions.randomUUID
import java.io.File
import java.io.InputStream

class ContentManager(
    private val cloudinary: Cloudinary
) {

    enum class Type(val value: String) {
        AVATAR("avatar"), POST("post")
    }

    fun upload(stream: InputStream, type: Type): String {
        cloudinary
        val image = File("temp", "${randomUUID()}_${System.currentTimeMillis()}")
        image.writeBytes(stream.buffered().use { it.readBytes() })

        val response = cloudinary.uploader().upload(
            image,
            ObjectUtils.asMap("public_id", randomUUID(), "folder", type.value)
        )
        image.delete()

        return response["url"].toString()
    }

}
