package com.linc.routes

import com.linc.data.network.dto.response.BaseResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.publicTest() {
    get("/refresh") {
        val response = BaseResponse("success", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBdXRoZW50aWNhdGlvbiIsImlzcyI6Imt0b3IuaW8iLCJpZCI6ImEzOTJhZDVkLTczYTgtNDk2MC1hYTZkLWQ1Nzk2YTJhNjg5YSJ9.P1NkS9Z9I1SiSoPTlM2ar5NEnwl1mFOCZQY_Gk09z28")
        call.defaultTextContentType(ContentType.Application.Json)
        call.respond(HttpStatusCode.OK, response)
    }
}