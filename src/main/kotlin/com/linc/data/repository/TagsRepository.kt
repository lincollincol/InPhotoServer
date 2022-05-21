package com.linc.data.repository

import com.linc.data.database.dao.PostTagDao
import com.linc.data.database.dao.TagDao
import com.linc.data.database.entity.post.TagEntity
import com.linc.model.TagModel
import com.linc.model.mappers.toModel
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TagsRepository(
    private val tagDao: TagDao,
    private val postTagDao: PostTagDao
) {

    suspend fun getTag(tagId: String): TagModel = withContext(Dispatchers.IO) {
        return@withContext tagDao.getTagById(tagId.toUUID())
            .getOrNull()
            ?.toModel()
            ?: error("Cannot load tags!")
    }

    suspend fun getTags(): List<TagModel> = withContext(Dispatchers.IO) {
        return@withContext tagDao.getTags()
            .getOrNull()
            ?.map(TagEntity::toModel)
            ?: error("Cannot load tags!")
    }

    suspend fun searchTags(query: String): List<TagModel> = withContext(Dispatchers.IO) {
        return@withContext tagDao.searchTagByQuery(query)
            .getOrNull()
            ?.map(TagEntity::toModel)
            ?: error("Cannot load tags!")
    }
}