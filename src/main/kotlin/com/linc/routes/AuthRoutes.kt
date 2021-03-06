package com.linc.routes

import com.linc.data.network.dto.request.auth.SignInDTO
import com.linc.data.network.dto.request.auth.SignUpDTO
import com.linc.data.repository.AuthRepository
import com.linc.data.repository.MediaRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.auth() {

    val authRepository: AuthRepository by inject()
    val userRepository: UsersRepository by inject()
    val mediaRepository: MediaRepository by inject()

    post<SignUpDTO>("/auth/sign-up") { request ->
        try {
            val userId = authRepository.signUp(request)
            val generatedAvatarUrl = mediaRepository.loadRandomAvatarUrl(request.gender)
            val generatedHeaderUrl = mediaRepository.loadRandomHeaderUrl()
            val user = with(userRepository) {
                updateUserAvatar(userId, generatedAvatarUrl)
                updateUserHeader(userId, generatedHeaderUrl)
                getExtendedUserById(userId)
            }
            call.respondSuccess(user)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post<SignInDTO>("/auth/sign-in") { request ->
        try {
            val userId = authRepository.signIn(request)
            call.respondSuccess(userRepository.getExtendedUserById(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }
}