package com.linc.routes

import com.linc.data.dto.request.users.UpdateNameDTO
import com.linc.data.dto.request.users.UpdateStatusDTO
import com.linc.data.dto.request.users.UpdateVisibilityDTO
import com.linc.data.repository.UsersRepository
import com.linc.utils.ImageUtils
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File

fun Route.users() {

    val usersRepository: UsersRepository by inject()
    val imageUtils: ImageUtils by inject()

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
        var image: File? = null

        data.forEachPart { part ->
            if(part is PartData.FileItem) {
                image = File(part.originalFileName!!).apply {
                    writeBytes(part.streamProvider().readBytes())
                }
            }
        }

        if(image == null) {
            call.respondFailure("Image not found!")
            return@post
        }

        val compressed = imageUtils.compressImage(
            image!!, ImageUtils.CompressType.STANDARD_IMAGE
        )

        usersRepository.updateUserAvatar(userId, compressed).fold(
            onSuccess = { call.respondSuccess(Unit) },
            onFailure =  { call.respondSuccess(it.errorMessage()) }
        )
    }

    post("/users/get-avatar/{id}") {
        val userId = call.parameters["id"].toString()
        usersRepository.getUserAvatar(userId).fold(
            onSuccess = { content ->
                val avatar = File("${content.id}.${content.extension}").apply {
                    writeBytes(content.data)
                }
                call.respondFile(avatar)
            },
            onFailure =  { call.respondSuccess(it.errorMessage()) }
        )
    }

}