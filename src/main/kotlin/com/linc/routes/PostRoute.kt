package com.linc.routes

import com.linc.data.network.dto.request.post.UpdatePostDTO
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.PostsRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.extractStringBody
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
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

    get("/ppp/{postId}/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            val postId = call.parameters["postId"].toString()
            postsRepository.getExtendedPost2(postId, userId)
            call.respondSuccess(Unit)
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

    post("/posts/{userId}") { request ->
        try {
            val multipartParts = call.receiveMultipart().readAllParts()
            val data = multipartParts.filterIsInstance<PartData.FileItem>().firstOrNull()
            val formParameters = multipartParts.filterIsInstance<PartData.FormItem>()
            val description = formParameters.firstOrNull { it.name == "description" }?.value.orEmpty()
            val tags = formParameters.filter { it.name == "tag" }.map { it.value }
            val userId = call.parameters["userId"].toString()
            val imageUrl: String? = data?.let { mediaRepository.uploadPost(it.streamProvider()) }
            if (imageUrl == null) {
                call.respondFailure("Image not found!")
                return@post
            }
            postsRepository.createPost(userId, imageUrl, description, tags)
            call.respondSuccess(null)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respondFailure(e.errorMessage())
        }
    }

    put<UpdatePostDTO>("/posts/{postId}") { request ->
        try {
            val postId = call.parameters["postId"].toString()
            postsRepository.updatePost(postId, request.description, request.tags)
            call.respondSuccess(null)
        } catch (e: Exception) {
            e.printStackTrace()
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
            val updatedPost = postsRepository.likePost(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                call.parameters["liked"].toBoolean(),
            )
            call.respondSuccess(updatedPost)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/posts/{postId}/bookmark/{userId}") {
        try {
            val updatedPost = postsRepository.bookmarkPost(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                call.parameters["bookmarked"].toBoolean(),
            )
            call.respondSuccess(updatedPost)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/posts/{postId}/comments") {
        try {
            val comments = postsRepository.getPostComments(call.parameters["postId"].toString())
            call.respondSuccess(comments)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<String>("/posts/{postId}/comments/{userId}") { body ->
        try {
            val commentEntity = postsRepository.createPostComment(
                call.parameters["postId"].toString(),
                call.parameters["userId"].toString(),
                body.extractStringBody()
            )
            call.respondSuccess(commentEntity)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    put<String>("/posts/comments/{commentId}") { body ->
        try {
            postsRepository.updatePostComment(
                call.parameters["commentId"].toString(),
                body.extractStringBody()
            )
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