package com.linc.data.repository

import com.linc.data.database.dao.PostDao
import com.linc.data.database.dao.PostTagDao
import com.linc.data.database.dao.TagDao
import com.linc.data.database.entity.ExtendedPostEntity
import com.linc.data.network.dto.request.post.PostDTO2
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.util.*

class PostsRepository(
    private val postDao: PostDao,
    private val tagDao: TagDao,
    private val postTagDao: PostTagDao
) {

    suspend fun createPost(
        contentUrl: String,
        request: PostDTO2
    ) = withContext(Dispatchers.IO) {
        val tagsIds = request.tags.map { tag ->
            async { tagDao.createTag(tag).getOrNull() }
        }

        val postId = postDao.createPost(
            UUID.fromString(request.userId),
            contentUrl,
            request.description
        ).getOrNull() ?: throw Exception("Cannot create post!")

        tagsIds.awaitAll()
            .filterNotNull()
            .map { tagId ->
                async { postTagDao.createPostTag(tagId, postId).getOrNull() }
            }
            .awaitAll()
    }

    suspend fun updatePost(
        postId: String,
        request: PostDTO2
    ) = withContext(Dispatchers.IO) {
        postDao.updatePost(postId.toUUID(), request.url, request.description).getOrNull()
            ?: throw Exception("Cannot update post!")

        postTagDao.deletePostTagByPostId(postId.toUUID()).getOrNull()
            ?: throw Exception("Cannot delete post tags!")

        request.tags.map { tag ->
            async {
                tagDao.createTag(tag).getOrNull()?.let {
                    postTagDao.createPostTag(it, postId.toUUID())
                }
            }
        }.awaitAll()
    }

    suspend fun getUserPosts(
        userId: String
    ): List<ExtendedPostEntity> = withContext(Dispatchers.IO) {
        return@withContext postDao.getPostsByUserId(userId.toUUID()).getOrNull()
            ?: throw Exception("Cannot load user posts!")
    }

    suspend fun getPost(
        postId: String
    ): ExtendedPostEntity = withContext(Dispatchers.IO) {
        return@withContext postDao.getPostById(postId.toUUID()).getOrNull()
            ?: throw Exception("Post not found!")
    }

    suspend fun deletePost(postId: String) = withContext(Dispatchers.IO) {
        postTagDao.deletePostTagByPostId(postId.toUUID())
        // TODO: 14.03.22 delete likes
        // TODO: 14.03.22 delete comments
        postDao.deletePost(postId.toUUID())
    }

}