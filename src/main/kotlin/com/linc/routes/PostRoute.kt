package com.linc.routes

import com.linc.data.network.dto.request.post.CreatePostDTO
import com.linc.data.network.dto.request.post.CreatePostDTO2
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

    /*post<CreatePostDTO>("/post") { request ->
        try {

        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }*/

    get("/user-posts/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            val posts = postsRepository.getUserPosts(userId)
            call.respondSuccess(posts)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/user-post/{postId}") {

    }

    post<CreatePostDTO2>("/post") { request ->
        try {
            postsRepository.createPost(request.url, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    delete("/posts/{id}") {
        try {

        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    put<CreatePostDTO>("/post/{id}") {

    }

}