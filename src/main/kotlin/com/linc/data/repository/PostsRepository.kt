package com.linc.data.repository

import com.linc.data.database.dao.*
import com.linc.data.database.entity.post.ExtendedPostEntity
import com.linc.data.network.dto.request.post.CommentDTO
import com.linc.data.network.dto.request.post.PostDTO2
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import java.util.*

class PostsRepository(
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val bookmarkDao: BookmarkDao,
    private val likeDao: LikeDao,
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

    suspend fun getPosts(
        userId: String
    ): List<ExtendedPostEntity> = withContext(Dispatchers.IO) {
        return@withContext postDao.getPostsByPostId(userId.toUUID()).getOrNull()
            ?: throw Exception("Posts not found!")
    }

    suspend fun getPost(
        postId: String,
        userId: String
    ): ExtendedPostEntity = withContext(Dispatchers.IO) {
        return@withContext postDao.getPostByPostId(postId.toUUID(), userId.toUUID()).getOrNull()
            ?: throw Exception("Post not found!")
    }

    suspend fun deletePost(
        postId: String
    ) = withContext(Dispatchers.IO) {
        postDao.deletePost(postId.toUUID()).getOrNull() ?: throw Exception("Post not found!")
    }

    suspend fun getPostComments(postId: String) = withContext(Dispatchers.IO) {
        commentDao.getComments(postId.toUUID()).getOrNull()
            ?: throw Exception("Cannot load post comments!")
    }

    suspend fun createPostComment(
        postId: String,
        userId: String,
        commentDto: CommentDTO
    ) = withContext(Dispatchers.IO) {
        commentDao.createComment(postId.toUUID(), userId.toUUID(), commentDto.text).getOrNull()
            ?: throw Exception("Cannot create post comment!")
    }

    suspend fun updatePostComment(
        commentId: String,
        commentDto: CommentDTO
    ) = withContext(Dispatchers.IO) {
        commentDao.updateComment(commentId.toUUID(), commentDto.text).getOrNull()
            ?: throw Exception("Cannot update post comment!")
    }

    suspend fun deletePostComment(
        commentId: String
    ) = withContext(Dispatchers.IO) {
        commentDao.deleteComment(commentId.toUUID()).getOrNull()
            ?: throw Exception("Cannot update post comment!")
    }

    suspend fun likePost(
        postId: String,
        userId: String,
        isLiked: Boolean
    ) = withContext(Dispatchers.IO) {
        when {
            isLiked -> likeDao.createLike(postId.toUUID(), userId.toUUID())
            else -> likeDao.deleteLike(postId.toUUID(), userId.toUUID())
        }.getOrNull() ?: throw Exception("Cannot process like operation!")
    }

    suspend fun bookmarkPost(
        postId: String,
        userId: String,
        isBookmarked: Boolean
    ) = withContext(Dispatchers.IO) {
        when {
            isBookmarked -> bookmarkDao.createBookmark(postId.toUUID(), userId.toUUID())
            else -> bookmarkDao.deleteBookmark(postId.toUUID(), userId.toUUID())
        }.getOrNull() ?: throw Exception("Cannot process bookmark operation!")
    }

}