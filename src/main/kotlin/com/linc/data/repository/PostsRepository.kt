package com.linc.data.repository

import com.linc.data.database.dao.*
import com.linc.data.database.entity.post.ExtendedPostEntity
import com.linc.data.database.entity.post.PostEntity
import com.linc.data.network.dto.request.post.CommentDTO
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class PostsRepository(
    private val postDao: PostDao,
    private val commentDao: CommentDao,
    private val bookmarkDao: BookmarkDao,
    private val likeDao: LikeDao,
    private val tagDao: TagDao,
    private val postTagDao: PostTagDao
) {

    suspend fun createPost(
        userId: String,
        contentUrl: String,
        description: String,
        tags: List<String>
    ) = withContext(Dispatchers.IO) {
        val tagsIds = tags.map { tag ->
            async { tagDao.createTag(tag).getOrNull() }
        }

        val postId = postDao.createPost(userId.toUUID(), contentUrl, description)
            .getOrNull() ?: throw Exception("Cannot create post!")

        tagsIds.awaitAll()
            .filterNotNull()
            .map { tagId ->
                async { postTagDao.createPostTag(tagId, postId).getOrNull() }
            }
            .awaitAll()
    }

    suspend fun updatePost(
        postId: String,
        description: String,
        tags: List<String>
    ) = withContext(Dispatchers.IO) {
        postDao.updatePost(postId.toUUID(), description).getOrNull()
            ?: throw Exception("Cannot update post!")

        postTagDao.deletePostTagByPostId(postId.toUUID()).getOrNull()
            ?: throw Exception("Cannot delete post tags!")

        tags.map { tag ->
            async {
                tagDao.createTag(tag).getOrNull()?.let {
                    postTagDao.createPostTag(it, postId.toUUID())
                }
            }
        }.awaitAll()
    }

    suspend fun getUserExtendedPosts(
        userId: String
    ): List<ExtendedPostEntity> = withContext(Dispatchers.IO) {
        return@withContext postDao.getExtendedPostsByUserId(userId.toUUID()).getOrNull()
            ?: throw Exception("Cannot load user posts!")
    }

    suspend fun getExtendedPosts(
        userId: String
    ): List<ExtendedPostEntity> = withContext(Dispatchers.IO) {
        return@withContext postDao.getExtendedPosts(userId.toUUID()).getOrNull()
            ?: throw Exception("Posts not found!")
    }

    suspend fun getExtendedPost(
        postId: String,
        userId: String
    ): ExtendedPostEntity = withContext(Dispatchers.IO) {
        return@withContext postDao.getExtendedPostByPostId(postId.toUUID(), userId.toUUID()).getOrNull()
            ?: throw Exception("Post not found!")
    }

    suspend fun getExtendedPost2(
        postId: String,
        userId: String
    ) = withContext(Dispatchers.IO) {
        postDao.getExtendedPostByPostId2(postId.toUUID(), userId.toUUID()).getOrNull()
            ?: throw Exception("Post not found!")
        return@withContext
    }

    suspend fun getPosts(): List<PostEntity> = withContext(Dispatchers.IO) {
        return@withContext postDao.getPosts().getOrNull()
            ?: throw Exception("Posts not found!")
    }

    suspend fun getPost(
        postId: String
    ): PostEntity = withContext(Dispatchers.IO) {
        return@withContext postDao.getPostByPostId(postId.toUUID()).getOrNull()
            ?: throw Exception("Post not found!")
    }

    suspend fun getUserPosts(
        userId: String
    ): List<PostEntity> = withContext(Dispatchers.IO) {
        return@withContext postDao.getPostsByUserId(userId.toUUID()).getOrNull()
            ?: throw Exception("Cannot load user posts!")
    }

    suspend fun deletePost(postId: String) = withContext(Dispatchers.IO) {
        likeDao.deleteLikes(postId.toUUID()).getOrNull() ?: throw Exception("Cannot delete likes!")
        commentDao.deleteComment(postId.toUUID()).getOrNull() ?: throw Exception("Cannot delete comments!")
        postTagDao.deletePostTagByPostId(postId.toUUID()).getOrNull() ?: throw Exception("Cannot delete tags!")
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
    ): ExtendedPostEntity = withContext(Dispatchers.IO) {
        when {
            isLiked -> likeDao.createLike(postId.toUUID(), userId.toUUID())
            else -> likeDao.deleteLike(postId.toUUID(), userId.toUUID())
        }.getOrNull() ?: throw Exception("Cannot process like operation!")
        return@withContext getExtendedPost(postId, userId)
    }

    suspend fun bookmarkPost(
        postId: String,
        userId: String,
        isBookmarked: Boolean
    ): ExtendedPostEntity = withContext(Dispatchers.IO) {
        when {
            isBookmarked -> bookmarkDao.createBookmark(postId.toUUID(), userId.toUUID())
            else -> bookmarkDao.deleteBookmark(postId.toUUID(), userId.toUUID())
        }.getOrNull() ?: throw Exception("Cannot process bookmark operation!")
        return@withContext getExtendedPost(postId, userId)
    }

}