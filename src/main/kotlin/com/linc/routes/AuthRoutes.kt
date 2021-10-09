package com.linc.routes

import com.linc.data.dto.request.auth.SignUpRequestDTO
import com.linc.data.dto.response.BaseResponse
import com.linc.data.dto.toDTO
import com.linc.data.repository.AuthRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondOk
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.auth() {

    val repository: AuthRepository by inject()

    post<SignUpRequestDTO>("/auth/sign-up") { request ->
        repository.signUp(request).fold(
            onSuccess = { userEntity ->
                call.respondSuccess(userEntity.toDTO())
            },
            onFailure = {
                call.respondFailure(it.errorMessage())
            }
        )
    }

    post<SignUpRequestDTO>("/auth/sign-in") { request ->
        repository.signIn(request).fold(
            onSuccess = { userEntity ->
                call.respondSuccess(userEntity.toDTO())
            },
            onFailure = {
                call.respondFailure(it.errorMessage())
            }
        )
    }
}