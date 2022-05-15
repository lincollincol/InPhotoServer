package com.linc.routes

import com.linc.data.repository.ChatsRepository
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.extractStringBody
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.chats() {

    val usersRepository: UsersRepository by inject()
    val chatsRepository: ChatsRepository by inject()
    val mediaRepository: MediaRepository by inject()

    get("/chats") {

    }

    post<String>("/chats/{userId}") { body ->
        try {
            val senderUserId = call.parameters["userId"].toString()
            val receiverUserId = body.extractStringBody()
            chatsRepository.createChat(listOf(senderUserId, receiverUserId))
            call.respondSuccess(Unit)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}