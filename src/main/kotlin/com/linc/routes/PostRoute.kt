package com.linc.routes

import com.linc.data.network.dto.request.post.CommentDTO
import com.linc.data.network.dto.request.post.PostDTO2
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.PostsRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.posts() {

    val postsRepository: PostsRepository by inject()
    val mediaRepository: MediaRepository by inject()

    get("/posts-extended/users/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(postsRepository.getUserExtendedPosts(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/posts/users/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(postsRepository.getUserPosts(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/posts/{postId}") {
        try {
            val postId = call.parameters["postId"].toString()
            call.respondSuccess(postsRepository.getPost(postId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/posts-extended/{postId}/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            val postId = call.parameters["postId"].toString()
            call.respondSuccess(postsRepository.getExtendedPost(postId, userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/posts-extended/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(postsRepository.getExtendedPosts(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<PostDTO2>("/posts") { request ->
        try {
            postsRepository.createPost(request.url, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    put<PostDTO2>("/posts/{postId}") { request ->
        try {
            val postId = call.parameters["postId"].toString()
            postsRepository.updatePost(postId, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    delete("/posts/{postId}") {
        try {
            val postId = call.parameters["postId"].toString()
            postsRepository.deletePost(postId)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/posts/{postId}/like/{userId}") {
        try {
            postsRepository.likePost(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                true
            )
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    delete("/posts/{postId}/like/{userId}") {
        try {
            postsRepository.likePost(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                false
            )
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/posts/{postId}/bookmark/{userId}") {
        try {
            postsRepository.bookmarkPost(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                true
            )
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    delete("/posts/{postId}/bookmark/{userId}") {
        try {
            postsRepository.bookmarkPost(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                false
            )
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/posts/{postId}/comments") {
        try {
            postsRepository.getPostComments(call.parameters["postId"].toString())
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<CommentDTO>("/posts/{postId}/comments/{userId}") { body ->
        try {
            postsRepository.createPostComment(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                body
            )
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    put<CommentDTO>("/posts/comments/{commentId}") { body ->
        try {
            postsRepository.updatePostComment(call.parameters["commentId"].toString(), body)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    delete("/posts/comments/{commentId}") {
        try {
            postsRepository.deletePostComment(call.parameters["commentId"].toString())
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}