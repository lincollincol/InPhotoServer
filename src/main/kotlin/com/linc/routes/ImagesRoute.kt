package com.linc.routes

import com.linc.entity.PhotoEntity
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

//private const val BASE_URL = "http://localhost:8885"
// ipv4: hostname -I
private const val BASE_URL = "http://192.168.88.1:8885"

private val data = mutableListOf<PhotoEntity>().apply {
    repeat(100) {
        add(PhotoEntity("Photo $it", "url"))
    }
}

fun Route.photos() {

//    val repository = AccountsRepository()

   /* get("/auth/sign-uppppppp") {
        try {
            repository.saveAccount(
                name = null,
                email = "xlincollincolx@gmail.com",
                password = "12345678"
            )
            call.respond(HttpStatusCode.OK, "User created!")
        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(HttpStatusCode.OK, e.localizedMessage)
        }
    }*/

    /*get("/photo/fetch") {
        val count = call.parameters["count"]?.toInt() ?: 1
        databaseManager.getAllUsers().forEach {
            println(it.data)
        }

//        databaseManager.getAllPhotos().forEach {
//            it.id
//        }

        call.respond(
            HttpStatusCode.OK,
            "Success!"
//            when {
//                count > data.size -> data.last()
//                else -> data.take(count)
//            }
        )
    }*/

    post<PhotoEntity>("/photo/upload") {
//        val photo = call.receive<PhotoEntity>()
        println(it)
        call.respond(HttpStatusCode.OK)
    }

    post("/photo/random") {
        val data = call.receive<Map<String, String>>()
        data.forEach {
            println("${it.key} ${it.value}")
        }
        call.respond(HttpStatusCode.OK)
    }

    post("/photo/string") {
        val data = call.receive<String>()
        println(data)
        call.respond(HttpStatusCode.OK)
    }

    /*post("/photo/acc") {
        val data = call.receive<AccountEntity>()
        println(data)
        call.respond(HttpStatusCode.OK)
    }*/

}