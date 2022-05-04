package com.linc.routes

import com.linc.data.database.entity.user.Gender
import com.linc.data.repository.MediaRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.content() {

    val mediaRepository: MediaRepository by inject()

    get("/content/random-avatar") {
        val gender = Gender.fromString(call.parameters["gender"])
        try {
            call.respondSuccess(mediaRepository.loadRandomAvatarUrl(gender))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/content/random-header") {
        try {
            call.respondSuccess(mediaRepository.loadRandomHeaderUrl())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}