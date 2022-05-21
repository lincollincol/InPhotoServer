package com.linc.routes

import com.linc.data.database.entity.user.Gender
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.extractStringBody
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

    get("/users") {
        try {
            val query = call.parameters["query"].orEmpty()
            val users = when {
                query.isEmpty() -> usersRepository.getExtendedUsers()
                else -> usersRepository.searchExtendedUsers(query)
            }
            call.respondSuccess(users)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/users/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(usersRepository.getExtendedUserById(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<String>("/users/{userId}/username") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserName(userId, request.extractStringBody())
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<String>("/users/{userId}/status") { request ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserStatus(userId, request.extractStringBody())
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
            call.respondSuccess(usersRepository.getExtendedUserById(userId))
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
            call.respondSuccess(usersRepository.getExtendedUserById(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<String>("/users/{userId}/avatar-url") { body ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserAvatar(userId, body.extractStringBody())
            call.respondSuccess(usersRepository.getExtendedUserById(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<String>("/users/{userId}/header-url") { body ->
        try {
            val userId = call.parameters["userId"].toString()
            usersRepository.updateUserHeader(userId, body.extractStringBody())
            call.respondSuccess(usersRepository.getExtendedUserById(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/users/{userId}/following") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(usersRepository.getUserFollowing(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/users/{userId}/followers") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(usersRepository.getUserFollowers(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/users/{userId}/followers-ids") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(usersRepository.getUserFollowersIds(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/users/{userId}/following-ids") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(usersRepository.getUserFollowingIds(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/users/{userId}/followers/{followerId}") {
        try {
            val userId = call.parameters["userId"].toString()
            val followerId = call.parameters["followerId"].toString()
            usersRepository.followUser(userId, followerId)
            call.respondSuccess(usersRepository.getExtendedUserById(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    delete("/users/{userId}/followers/{followerId}") {
        try {
            val userId = call.parameters["userId"].toString()
            val followerId = call.parameters["followerId"].toString()
            usersRepository.unfollowUser(userId, followerId)
            call.respondSuccess(usersRepository.getExtendedUserById(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}