package com.linc.routes

import com.linc.data.dto.request.auth.SignUpRequestDTO
import com.linc.data.dto.response.BaseResponse
import com.linc.data.dto.toDTO
import com.linc.data.repository.AccountsRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.auth() {

    /**
     * TODO:
     * a. Move access token to user table
     * b. Maybe separate nickname and email to new table
     */

    val repository: AccountsRepository by inject()

    post<SignUpRequestDTO>("/auth/sign-up") { request ->
        val operation = repository.createAccount(request)

        val userEntity = operation.getOrElse {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        call.respond(
            HttpStatusCode.OK,
            BaseResponse(1, userEntity.toDTO())
        )
    }
}