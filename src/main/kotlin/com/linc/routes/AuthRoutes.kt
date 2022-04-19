package com.linc.routes

import com.linc.data.network.AvatarManager
import com.linc.data.network.dto.request.auth.SignInDTO
import com.linc.data.network.dto.request.auth.SignUpDTO
import com.linc.data.network.mapper.toUserDto
import com.linc.data.repository.AuthRepository
import com.linc.data.repository.MediaRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.auth() {

    val authRepository: AuthRepository by inject()
    val mediaRepository: MediaRepository by inject()

    post<SignUpDTO>("/auth/sign-up") { request ->
        try {
            val generatedAvatarUrl = mediaRepository.generateAvatar(
                AvatarManager.Gender.FEMALE,
                request.username
            )
            val user = authRepository.signUp(request, generatedAvatarUrl)
            call.respondSuccess(user.toUserDto())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<SignInDTO>("/auth/sign-in") { request ->
        try {
            val user = authRepository.signIn(request)
            call.respondSuccess(user.toUserDto())
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }
}