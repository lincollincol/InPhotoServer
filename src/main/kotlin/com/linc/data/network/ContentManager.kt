package com.linc.data.network

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.linc.utils.extensions.randomUUID
import java.io.File
import java.io.InputStream

class ContentManager(
    private val cloudinary: Cloudinary
) {

    enum class Type(val value: String) {
        AVATAR("avatar"), FEED("feed")
    }

    fun upload(stream: InputStream, type: Type): String {
        cloudinary
        val image = File("temp", "${randomUUID()}_${System.currentTimeMillis()}")
        image.writeBytes(stream.buffered().use { it.readBytes() })

        val response = cloudinary.uploader().upload(
            image,
            ObjectUtils.asMap("public_id", randomUUID(), "folder", type.value)
        )
        image.delete()

        return response["url"].toString()
    }

    /*
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
  */

}
