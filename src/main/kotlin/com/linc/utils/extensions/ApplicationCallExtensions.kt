package com.linc.utils.extensions

import com.linc.data.network.dto.response.BaseResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*

suspend inline fun <reified T : Any> ApplicationCall.respondOk(responseBody: BaseResponse<T>) {
    response.status(HttpStatusCode.OK)
    respond(responseBody)
}

suspend inline fun <reified T : Any> ApplicationCall.respondSuccess(responseBody: T?) {
    respondOk(BaseResponse(HttpStatusCode.OK.description, false, responseBody))
}

suspend inline fun ApplicationCall.respondFailure(errorMessage: String) {
    respondOk(BaseResponse(errorMessage, true, null))
}
