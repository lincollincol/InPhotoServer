package com.linc.routes

import com.linc.data.network.dto.request.users.UpdateNameDTO
import com.linc.data.network.dto.request.users.UpdateStatusDTO
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.data.network.mapper.toUserDto
import com.linc.data.repository.MediaRepository
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
//    val imageUtils: ImageUtils by inject()
    val mediaRepository: MediaRepository by inject()
//    val contentManager: ContentManager by inject()

    post<UpdateNameDTO>("/users/update-name/{id}") { request ->
        try {
            val userId = call.parameters["id"].toString()
            usersRepository.updateUserName(userId, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<UpdateStatusDTO>("/users/update-status/{id}") { request ->
        try {
            val userId = call.parameters["id"].toString()
            usersRepository.updateUserStatus(userId, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<UpdateVisibilityDTO>("/users/update-visibility/{id}") { request ->
        try {
            val userId = call.parameters["id"].toString()
            usersRepository.updateUserVisibility(userId, request)
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/users/update-avatar/{id}") { request ->
        try {
            val userId = call.parameters["id"].toString()
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

    post("/users/get-avatar/{id}") {
        try {
            val userId = call.parameters["id"].toString()
            call.respondSuccess(usersRepository.getUserAvatar(userId).toString())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}