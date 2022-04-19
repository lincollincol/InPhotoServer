package com.linc.data.repository

import com.linc.data.database.dao.SystemContentDao
import com.linc.data.network.AvatarManager
import com.linc.data.network.ContentManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.io.InputStream

class MediaRepository(
    private val systemContentDao: SystemContentDao,
    private val contentManager: ContentManager,
    private val avatarManager: AvatarManager,
) {

    suspend fun generateAvatar(
        gender: AvatarManager.Gender,
        seed: String
    ): String = withContext(Dispatchers.IO) {
        val avatar = avatarManager.generateAvatar(gender, seed)
        return@withContext contentManager.upload(avatar, ContentManager.Directory.AVATAR)
    }

    fun uploadAvatar(data: InputStream): String {
        return contentManager.upload(data, ContentManager.Directory.AVATAR)
    }

    fun uploadPost(data: InputStream): String {
        return contentManager.upload(data, ContentManager.Directory.POST)
    }

    suspend fun loadImageUrls(dir: ContentManager.Directory) = withContext(Dispatchers.IO) {
        contentManager.loadContentUrls(dir)
    }

    suspend fun loadRandomHeaderUrl() = systemContentDao.getRandomSystemContentByDir(
        ContentManager.Directory.SYSTEM_HEADER.value
    ).getOrNull()?.url ?: throw Exception("Cannot load default header!")

    suspend fun syncWithRemoteSystemDirs() = withContext(Dispatchers.IO) {
        val directory = ContentManager.Directory.SYSTEM_HEADER
        contentManager.loadContentUrls(directory).map {
            async { systemContentDao.insertContentData(directory.value, it) }
        }.awaitAll()
    }


}