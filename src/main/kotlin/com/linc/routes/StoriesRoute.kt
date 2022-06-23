package com.linc.routes

import com.linc.data.repository.MediaRepository
import com.linc.data.repository.StoriesRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.stories() {

    val storiesRepository: StoriesRepository by inject()
    val mediaRepository: MediaRepository by inject()

    get("/stories") {
        try {
            val stories = storiesRepository.getStories()
            call.respondSuccess(stories)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/stories/{storyId}") {
        try {
            val storyId = call.parameters["storyId"].orEmpty()
            val story = storiesRepository.getStory(storyId)
            call.respondSuccess(story)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/stories/users/{userId}") {
        try {
            val userId = call.parameters["userId"].orEmpty()
            val stories = storiesRepository.getUserStories(userId)
            call.respondSuccess(stories)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/stories/users-following/{userId}") {
        try {
            val userId = call.parameters["userId"].toString()
            call.respondSuccess(storiesRepository.getUserFollowingStories(userId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    post("/stories/users/{userId}") { request ->
        try {
            val multipartParts = call.receiveMultipart().readAllParts()
            val data = multipartParts.filterIsInstance<PartData.FileItem>().firstOrNull()
            val formParameters = multipartParts.filterIsInstance<PartData.FormItem>()
            val expirationTimestamp = formParameters.firstOrNull { it.name == "expirationTimestamp" }
                ?.value?.toLongOrNull() ?: error("Invalid expires timestamp!")
            val durationMillis = formParameters.firstOrNull { it.name == "durationMillis" }
                ?.value?.toLongOrNull() ?: error("Invalid story duration!")
            val userId = call.parameters["userId"].toString()
            val imageUrl: String? = data?.let { mediaRepository.uploadStory(it.streamProvider()) }
            if (imageUrl == null) {
                call.respondFailure("Image not found!")
                return@post
            }
            storiesRepository.createStory(
                userId,
                imageUrl,
                System.currentTimeMillis() + expirationTimestamp,
                durationMillis
            )
            call.respondSuccess(null)
        } catch (e: Exception) {
            e.printStackTrace()
            call.respondFailure(e.errorMessage())
        }
    }

}