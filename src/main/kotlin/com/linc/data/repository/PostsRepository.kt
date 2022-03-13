package com.linc.data.repository

import com.linc.data.database.dao.PostDao
import com.linc.data.database.dao.PostTagDao
import com.linc.data.database.dao.TagDao
import com.linc.data.database.entity.ExtendedPostEntity
import com.linc.data.network.dto.request.post.CreatePostDTO2
import com.linc.utils.extensions.toUUID
import java.util.*

class PostsRepository(
    private val postDao: PostDao,
    private val tagDao: TagDao,
    private val postTagDao: PostTagDao
) {

    suspend fun createPost(
        contentUrl: String,
        request: CreatePostDTO2
    ) {
        val tagsIds = request.tags.mapNotNull { tag ->
            tagDao.getTagByName(tag).getOrNull()?.id?.toUUID()
                ?: tagDao.createTag(tag).getOrNull()
        }

        val postId = postDao.createPost(
            UUID.fromString(request.userId),
            contentUrl,
            request.description
        ).getOrNull() ?: throw Exception("Cannot create post!")

        tagsIds.forEach { tagId ->
            postTagDao.createPostTag(tagId, postId).getOrNull()
        }
//        return postDao.getPostById(postId).getOrNull() ?: throw Exception("Post not found!")
    }

    suspend fun getUserPosts(userId: String): List<ExtendedPostEntity> {
        return postDao.getPostsByUserId(userId.toUUID()).getOrNull()
            ?: throw Exception("Cannot load user posts!")
    }

}