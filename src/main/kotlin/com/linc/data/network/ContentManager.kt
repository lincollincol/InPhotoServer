package com.linc.data.network

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.linc.utils.extensions.HTTPS_URL_REGEX
import com.linc.utils.extensions.randomUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream


// https://support.cloudinary.com/hc/en-us/community/posts/360009534411-How-can-i-get-all-files-or-images-from-a-folder-
class ContentManager(
    private val cloudinary: Cloudinary
) {

    enum class Directory(val value: String) {
        AVATAR("avatar"),
        POST("post"),
        HEADER("header"),
        SYSTEM_HEADER("system/header")
    }

    fun upload(stream: InputStream, directory: Directory): String {
        val image = File("temp", "${randomUUID()}_${System.currentTimeMillis()}")
        image.writeBytes(stream.buffered().use { it.readBytes() })
        val url = upload(image, directory)
        image.delete()
        return url
    }

    fun upload(bytes: ByteArray, directory: Directory): String {
        val image = File("temp", "${randomUUID()}_${System.currentTimeMillis()}")
        image.writeBytes(bytes)
        val url = upload(image, directory)
        image.delete()
        return url
    }

    fun upload(image: File, directory: Directory): String {
        val response = cloudinary.uploader().upload(
            image,
            ObjectUtils.asMap("public_id", randomUUID(), "folder", directory.value)
        )
        image.delete()
        return response["url"].toString()
    }

    suspend fun loadContentUrls(directory: Directory): List<String> = withContext(Dispatchers.IO) {
        val urls = mutableListOf<String>()
        var nextCursor: String? = null
        do {
            val response = cloudinary.search()
                .expression("folder:\"${directory.value}\"")
                .maxResults(500)
                .nextCursor(nextCursor.orEmpty())
                .execute()
            nextCursor = response["next_cursor"] as? String
            urls += HTTPS_URL_REGEX.findAll(response["resources"].toString()).map { it.value }
        } while (nextCursor != null)
        return@withContext urls
    }

}