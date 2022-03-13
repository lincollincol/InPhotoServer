package com.linc.data.repository

import com.linc.data.network.ContentManager
import java.io.InputStream

class MediaRepository(
    private val contentManager: ContentManager
) {

    fun uploadAvatar(data: InputStream): String {
        return contentManager.upload(data, ContentManager.Type.AVATAR)
    }

    fun uploadPost(data: InputStream): String {
        return contentManager.upload(data, ContentManager.Type.POST)
    }

}