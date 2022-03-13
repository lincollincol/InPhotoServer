package com.linc.routes

import com.linc.data.network.ContentManager
import com.linc.data.network.dto.request.post.CreatePostDTO
import com.linc.data.network.dto.request.users.UpdateNameDTO
import com.linc.data.network.dto.request.users.UpdateStatusDTO
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.data.network.mapper.toUserDto
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.posts() {

    val usersRepository: UsersRepository by inject()
    val contentManager: ContentManager by inject()

    post<CreatePostDTO>("/post") { request ->
        try {
            /*val userId = call.parameters["id"].toString()
            val data = call.receiveMultipart()
            var imageUrl: String? = null

            data.forEachPart { part ->
                if (part is PartData.FileItem) {
                    imageUrl = contentManager.upload(
                        part.streamProvider(),
                        ContentManager.Type.AVATAR
                    )
                }
            }

            if (imageUrl == null) {
                call.respondFailure("Image not found!")
                return@post
            }

            usersRepository.updateUserAvatar(userId, imageUrl!!)
            val user = usersRepository.getExtendedUserById(userId)
            call.respondSuccess(user.toUserDto())*/
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
    /*post<UpdateNameDTO>("/users/update-name/{id}") { request ->
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

    post("/users/get-avatar/{id}") {
        try {
            val userId = call.parameters["id"].toString()
            call.respondSuccess(usersRepository.getUserAvatar(userId).toString())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }*/

}