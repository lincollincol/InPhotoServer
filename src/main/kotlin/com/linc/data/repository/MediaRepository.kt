package com.linc.data.repository

import com.linc.data.database.dao.SystemContentDao
import com.linc.data.database.entity.user.Gender
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

    /*suspend fun loadRandomAvatarUrl(
        seed: String,
        gender: Gender?
    ): String = withContext(Dispatchers.IO) {
        return@withContext avatarManager.getAvatarUrl(seed, gender)
    }

    suspend fun generateAvatar(
        seed: String,
        gender: Gender?
    ): String = withContext(Dispatchers.IO) {
        val avatar = avatarManager.generateAvatar(seed, gender)
        return@withContext contentManager.upload(avatar, ContentManager.Directory.AVATAR)
    }*/

    suspend fun uploadAvatar(data: InputStream): String = withContext(Dispatchers.IO) {
        return@withContext contentManager.upload(data, ContentManager.Directory.AVATAR)
    }

    suspend fun uploadHeader(data: InputStream): String = withContext(Dispatchers.IO) {
        return@withContext contentManager.upload(data, ContentManager.Directory.HEADER)
    }

    suspend fun uploadChatContent(data: InputStream): String = withContext(Dispatchers.IO) {
        return@withContext contentManager.upload(data, ContentManager.Directory.CHAT)
    }

    suspend fun uploadPost(data: InputStream): String = withContext(Dispatchers.IO) {
        return@withContext contentManager.upload(data, ContentManager.Directory.POST)
    }

    suspend fun loadImageUrls(dir: ContentManager.Directory) = withContext(Dispatchers.IO) {
        contentManager.loadContentUrls(dir)
    }

    suspend fun loadStickers() = systemContentDao
        .getSystemContentByDir(ContentManager.Directory.SYSTEM_STICKERS.value)
        .getOrNull()
        ?.map { it.url }
        ?: throw Exception("Cannot load default header!")

    suspend fun loadRandomHeaderUrl() =
        systemContentDao.getRandomSystemContentByDir(
            ContentManager.Directory.SYSTEM_HEADER.value
        ).getOrNull()?.url ?: throw Exception("Cannot load default header!")

    suspend fun loadRandomAvatarUrl(gender: Gender?) =
        systemContentDao.getRandomSystemContentByDir(
            when (gender) {
                Gender.MALE -> ContentManager.Directory.SYSTEM_AVATAR_MALE.value
                else -> ContentManager.Directory.SYSTEM_AVATAR_FEMALE.value
            }
        ).getOrNull()?.url ?: throw Exception("Cannot load default avatar!")

    suspend fun syncWithRemoteSystemDirs() = withContext(Dispatchers.IO) {
        val directories = listOf(
            ContentManager.Directory.SYSTEM_HEADER,
            ContentManager.Directory.SYSTEM_STICKERS,
            ContentManager.Directory.SYSTEM_AVATAR_MALE,
            ContentManager.Directory.SYSTEM_AVATAR_FEMALE
        )
        directories.map { directory ->
            async {
                contentManager.loadContentUrls(directory).forEach {
                    systemContentDao.insertContentData(directory.value, it)
                }
            }
        }.awaitAll()
//        contentManager.loadContentUrls(directory).map {
//            async { systemContentDao.insertContentData(directory.value, it) }
//        }.awaitAll()
    }

}