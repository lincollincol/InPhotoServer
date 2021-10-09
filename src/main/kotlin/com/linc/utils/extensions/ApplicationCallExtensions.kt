package com.linc.utils.extensions

import com.linc.data.dto.response.BaseResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

suspend inline fun <reified T : Any> ApplicationCall.respondOk(responseBody: BaseResponse<T>) {
    response.status(HttpStatusCode.OK)
    respond(responseBody)
}

public suspend inline fun <reified T : Any> ApplicationCall.respondSuccess(responseBody: T) {
    respondOk(BaseResponse(true, responseBody))
}

public suspend inline fun <reified T : Any> ApplicationCall.respondFailure(responseBody: T) {
    respondOk(BaseResponse(false, responseBody))
}
