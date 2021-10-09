package com.linc.routes

import com.linc.data.dto.request.users.UpdateUserNameRequestDTO
import com.linc.data.dto.response.BaseResponse
import com.linc.data.dto.toDTO
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.respondOk
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.users() {

    /**
     * TODO:
     * update username
     * update status
     * update account public access
     * update avatar
     * */

    val usersRepository: UsersRepository by inject()

    post<UpdateUserNameRequestDTO>("/users/update-username") { request ->
        val accessToken = call.request.authorization()

        println(call.principal<JWTPrincipal>()!!.payload.getClaim("id"))
        call.respondSuccess(accessToken!!)
        /*usersRepository.updateUserName(request).fold(
            onSuccess = { userEntity ->
                call.respondOk(BaseResponse(body = Unit))
            },
            onFailure = {
                call.respondOk(BaseResponse(body = it.message))
            }
        )*/
    }

}