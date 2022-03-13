package com.linc.data.repository

import com.linc.data.database.dao.PostDao
import com.linc.data.database.entity.PostEntity
import com.linc.data.database.entity.UserEntity
import com.linc.data.database.entity.UserExtendedEntity
import com.linc.data.network.dto.request.post.CreatePostDTO
import java.util.*

class PostsRepository(
    private val postDao: PostDao
) {

    suspend fun createPost(
        contentUrl: String,
        request: CreatePostDTO
    ): PostEntity {
        val postId = postDao.createPost(
            UUID.fromString(request.userId),
            contentUrl,
            request.description
        ).getOrNull() ?: throw Exception("Cannot create post!")

        return postDao.getPostById(postId).getOrNull() ?: throw Exception("Post not found!")

    }

}