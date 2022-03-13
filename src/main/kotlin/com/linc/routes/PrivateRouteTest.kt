package com.linc.routes

import com.linc.data.network.dto.response.BaseResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.privateTest() {
    get("/access") {
        call.defaultTextContentType(ContentType.Application.Json)
        call.respond(HttpStatusCode.OK, BaseResponse("success", false, "token-token"))
    }
}