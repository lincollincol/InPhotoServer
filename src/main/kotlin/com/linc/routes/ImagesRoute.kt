package com.linc.routes

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.linc.data.database.entity.PhotoEntity
import com.linc.utils.extensions.randomUUID
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File

//private const val BASE_URL = "http://localhost:8885"
// ipv4: hostname -I
//private const val BASE_URL = "http://192.168.88.1:8885"
private const val BASE_URL = "http://192.168.1.18:8885"


// home: 192.168.1.18

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

    /*
    import com.cloudinary.*;
...
Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
  "cloud_name", "dqildid2v",
  "api_key", "942731591311994",
  "api_secret", "6_ai7gKJmk0Jr543WHMvNeJ1ubY"));

  //For Gradle, add to dependencies section of build.gradle
compile group: 'com.cloudinary', name: 'cloudinary-http44', version: '[Cloudinary API version, e.g. 1.1.3]'
     */

    /*

     */

    post("/photo/string") {
//        val data = call.receive<String>()
//        println(data)

        val data = call.receiveMultipart()
        var image = File("temp", "${randomUUID()}_${System.currentTimeMillis()}")
        val cloudinary = Cloudinary("cloudinary://942731591311994:6_ai7gKJmk0Jr543WHMvNeJ1ubY@dqildid2v")


        data.forEachPart { part ->
            if(part is PartData.FileItem) {
                image.writeBytes(part.streamProvider().buffered().use { it.readBytes() })

                val response = cloudinary.uploader().upload(
                    image,
                    ObjectUtils.asMap("public_id", randomUUID(), "folder", "posts")
                )
                image.delete()

                println(response["url"])

            }
        }
        call.respond(HttpStatusCode.OK)
    }

    post("/photo/multipart") {
        val data = call.receiveMultipart()
        var image: File? = null

        data.forEachPart { part ->
            if(part is PartData.FileItem) {
                image = File(part.originalFileName!!).apply {
                    writeBytes(part.streamProvider().readBytes())
                }
            } else if(part is PartData.FormItem) {
                println("val form ${part.value}")
                println("val name ${part.name}")
            } else if(part is PartData.BinaryItem) {
                println("bin name ${part.name}")
            }
        }
        println(image?.name)
        call.respond(HttpStatusCode.OK)
    }

    /*post("/photo/acc") {
        val data = call.receive<AccountEntity>()
        println(data)
        call.respond(HttpStatusCode.OK)
    }*/

}