package com.linc.routes

import com.linc.data.repository.MediaRepository
import com.linc.data.repository.PostsRepository
import com.linc.data.repository.UsersRepository
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun Route.testRoute() {

    val usersRepository: UsersRepository by inject()
    val postsRepository: PostsRepository by inject()
    val mediaRepository: MediaRepository by inject()

    get("/test") {
        call.respondSuccess(mediaRepository.loadRandomHeaderUrl())
    }

    get("/test2") {
        call.respondSuccess("Hi 2")
    }

    get("/test3") {
        call.respondSuccess(UUID.randomUUID())
    }

    get("/test4") {
        val start = System.currentTimeMillis()
        println("Start request: ${getTime()}")
        val posts = postsRepository.getPosts()
        println("Fetched users: ${getTime()}, processing time ${(System.currentTimeMillis() - start) / 1000f}")
        call.respondSuccess(posts)
        println("End request: ${getTime()}, processing time ${(System.currentTimeMillis() - start) / 1000f}")
    }
}

fun getTime(): String {
    val date = Date(System.currentTimeMillis())
    val formatter: DateFormat = SimpleDateFormat("HH:mm:ss.SSS")
    formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
    return formatter.format(date)
}
