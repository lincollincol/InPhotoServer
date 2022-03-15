package com.linc.routes

import com.linc.data.network.dto.request.auth.SignInDTO
import com.linc.data.network.dto.request.auth.SignUpDTO
import com.linc.data.network.mapper.toUserDto
import com.linc.data.repository.AuthRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.auth() {

    val repository: AuthRepository by inject()

    post<SignUpDTO>("/auth/sign-up") { request ->
        repository.signUp(request).fold(
            onSuccess = { userEntity ->
                call.respondSuccess(userEntity.toUserDto())
            },
            onFailure = {
                call.respondFailure(it.errorMessage())
            }
        )
    }

    post<SignInDTO>("/auth/sign-in") { request ->
        repository.signIn(request).fold(
            onSuccess = { userEntity ->
                call.respondSuccess(userEntity.toUserDto())
            },
            onFailure = {
                call.respondFailure(it.errorMessage())
            }
        )
    }
}