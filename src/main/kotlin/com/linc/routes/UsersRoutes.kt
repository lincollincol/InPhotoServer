package com.linc.routes

import com.linc.data.network.dto.request.users.UpdateNameDTO
import com.linc.data.network.dto.request.users.UpdateStatusDTO
import com.linc.data.network.dto.request.users.UpdateVisibilityDTO
import com.linc.data.network.ContentManager
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.users() {

    val usersRepository: UsersRepository by inject()
//    val imageUtils: ImageUtils by inject()
    val contentManager: ContentManager by inject()

    post<UpdateNameDTO>("/users/update-name/{id}") { request ->
        val userId = call.parameters["id"].toString()
        usersRepository.updateUserName(userId, request).fold(
            onSuccess = { call.respondSuccess(Unit) },
            onFailure = { call.respondFailure(it.errorMessage()) }
        )
    }

    post<UpdateStatusDTO>("/users/update-status/{id}") { request ->
        val userId = call.parameters["id"].toString()
        usersRepository.updateUserStatus(userId, request).fold(
            onSuccess = { call.respondSuccess(Unit) },
            onFailure = { call.respondFailure(it.errorMessage()) }
        )
    }

    post<UpdateStatusDTO>("/users/update-status/{id}") { request ->
        val userId = call.parameters["id"].toString()
        usersRepository.updateUserStatus(userId, request).fold(
            onSuccess = { call.respondSuccess(Unit) },
            onFailure = { call.respondFailure(it.errorMessage()) }
        )
    }

    post<UpdateVisibilityDTO>("/users/update-visibility/{id}") { request ->
        val userId = call.parameters["id"].toString()
        usersRepository.updateUserVisibility(userId, request).fold(
            onSuccess = { call.respondSuccess(Unit) },
            onFailure = { call.respondFailure(it.errorMessage()) }
        )
    }

    post("/users/update-avatar/{id}") { request ->
        val userId = call.parameters["id"].toString()

        val data = call.receiveMultipart()
        var imageUrl: String? = null

        data.forEachPart { part ->
            if(part is PartData.FileItem) {
                imageUrl = contentManager.upload(part.streamProvider())
            }
        }

        if(imageUrl == null) {
            call.respondFailure("Image not found!")
            return@post
        }

        usersRepository.updateUserAvatar(userId, imageUrl!!).fold(
            onSuccess = { call.respondSuccess(Unit) },
            onFailure =  { call.respondSuccess(it.errorMessage()) }
        )
    }

    post("/users/get-avatar/{id}") {
        val userId = call.parameters["id"].toString()
        call.respondSuccess(usersRepository.getUserAvatar(userId))
    }

}