package com.linc.routes

import com.linc.data.network.dto.request.users.UpdateNameDTO
import com.linc.data.network.dto.request.users.UpdateStatusDTO
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.data.network.mapper.toUserDto
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.PostsRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.getFileData
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.users() {

    val usersRepository: UsersRepository by inject()
    val mediaRepository: MediaRepository by inject()
    val postsRepository: PostsRepository by inject()

    post<UpdateNameDTO>("/users/{userId}/username") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserName(userId, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<UpdateStatusDTO>("/users/{userId}/status") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserStatus(userId, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<UpdateVisibilityDTO>("/users/{userId}/visibility") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserVisibility(userId, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/users/{userId}/avatar") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            val data = call.receiveMultipart().getFileData()
            val imageUrl: String? = data?.let { mediaRepository.uploadAvatar(it) }

            if (imageUrl == null) {
                call.respondFailure("Image not found!")
                return@post
            }
            usersRepository.updateUserAvatar(userId, imageUrl)
            val user = usersRepository.getExtendedUserById(userId)
            call.respondSuccess(user.toUserDto())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/users/{userId}/posts") {
        try {
            val userId = call.parameters["userId"].toString()
            val posts = postsRepository.getUserPosts(userId)
            call.respondSuccess(posts)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}