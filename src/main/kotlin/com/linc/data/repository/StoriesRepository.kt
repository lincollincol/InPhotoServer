package com.linc.data.repository

import com.linc.data.database.dao.StoriesDao
import com.linc.data.database.dao.UserDao
import com.linc.data.database.entity.story.StoryEntity
import com.linc.data.database.entity.story.UserStoryEntity
import com.linc.model.StoryModel
import com.linc.model.UserStoryModel
import com.linc.model.mappers.toModel
import com.linc.model.mappers.toStoriesModel
import com.linc.utils.extensions.toUUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class StoriesRepository(
    private val storiesDao: StoriesDao,
    private val userDao: UserDao,
) {

    suspend fun createStory(
        userId: String,
        contentUrl: String,
        expiresTimestamp: Long,
        durationMillis: Long
    ) = withContext(Dispatchers.IO) {
        storiesDao.createUserStory(
            userId.toUUID(),
            contentUrl,
            durationMillis,
            expiresTimestamp
        ).getOrNull() ?: error("Cannot create story!")
    }

    suspend fun getStories(): List<StoryModel> = withContext(Dispatchers.IO) {
        storiesDao.getStories()
            .getOrNull()
            ?.map(StoryEntity::toModel)
            ?: error("Cannot load stories!")
    }

    suspend fun getStory(storyId: String): StoryModel? = withContext(Dispatchers.IO) {
        storiesDao.getStoryById(storyId.toUUID())
            .getOrNull()
            ?.toModel()
    }

    suspend fun getUserStories(
        userId: String
    ): UserStoryModel = withContext(Dispatchers.IO) {
        val user = async {
            userDao.getUserById(userId.toUUID())
                .getOrNull()
                ?: error("User not found!")
        }
        val stories = async {
            storiesDao.getStoriesByUser(userId.toUUID())
                .getOrNull()
                ?: error("Cannot load user stories!")
        }
        return@withContext user.await().toStoriesModel(stories.await())
    }

    suspend fun getUserFollowingStories(
        userId: String
    ): List<UserStoryModel> = withContext(Dispatchers.IO) {
        val stories = storiesDao.getUserFollowingStories(userId.toUUID())
            .getOrNull()
            ?.map(UserStoryEntity::toStoriesModel)
            ?: error("Cannot load user following stories!")
//        return@withContext user.await().toStoriesModel(stories.await())
        return@withContext stories
    }

    suspend fun getUserFollowingStoryPreview(
        userId: String
    ): List<UserStoryModel> = withContext(Dispatchers.IO) {
        val stories = storiesDao.getUserFollowingStories(userId.toUUID())
            .getOrNull()
            ?.map(UserStoryEntity::toStoriesModel)
            ?: error("Cannot load user following stories!")
//        return@withContext user.await().toStoriesModel(stories.await())
        return@withContext stories
    }

}