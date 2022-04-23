package com.linc.routes

import com.linc.data.database.entity.user.Gender
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.data.network.mapper.toUserDto
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.PostsRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.removeQuotes
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.users() {

    val usersRepository: UsersRepository by inject()
    val mediaRepository: MediaRepository by inject()
    val postsRepository: PostsRepository by inject()

    post<String>("/users/{userId}/username") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserName(userId, request.removeQuotes())
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<String>("/users/{userId}/status") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserStatus(userId, request.removeQuotes())
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<String>("/users/{userId}/gender") { body ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserGender(userId, Gender.fromString(body))
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
            val data = call.receiveMultipart()
                .readAllParts()
                .filterIsInstance<PartData.FileItem>()
                .firstOrNull()

            val imageUrl: String = data?.let { mediaRepository.uploadAvatar(it.streamProvider()) }
                ?: throw Exception("Image not found!")

            usersRepository.updateUserAvatar(userId, imageUrl)
            val user = usersRepository.getExtendedUserById(userId)
            call.respondSuccess(user.toUserDto())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/users/{userId}/header") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            val data = call.receiveMultipart()
                .readAllParts()
                .filterIsInstance<PartData.FileItem>()
                .firstOrNull()

            val imageUrl: String = data?.let { mediaRepository.uploadHeader(it.streamProvider()) }
                ?: throw Exception("Image not found!")

            usersRepository.updateUserHeader(userId, imageUrl)
            val user = usersRepository.getExtendedUserById(userId)
            call.respondSuccess(user.toUserDto())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}