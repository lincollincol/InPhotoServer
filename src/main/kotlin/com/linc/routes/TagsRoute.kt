package com.linc.routes

import com.linc.data.repository.TagsRepository
import com.linc.utils.extensions.errorMessage
import com.linc.utils.extensions.respondFailure
import com.linc.utils.extensions.respondSuccess
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Route.tags() {

    val tagsRepository: TagsRepository by inject()

    get("/tags") {
        try {
            val query = call.parameters["query"].orEmpty()
            val tags = when {
                query.isEmpty() -> tagsRepository.getTags()
                else -> tagsRepository.searchTags(query)
            }
            call.respondSuccess(tags)
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

    get("/tags/{tagId}") {
        try {
            val tagId = call.parameters["tagId"].orEmpty()
            call.respondSuccess(tagsRepository.getTag(tagId))
        } catch (e: Exception) {
            call.respondFailure(e.errorMessage())
        }
    }

}