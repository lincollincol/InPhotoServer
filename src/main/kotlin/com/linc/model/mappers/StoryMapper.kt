package com.linc.model.mappers

import com.linc.data.database.entity.story.StoryEntity
import com.linc.data.database.entity.story.UserStoryEntity
import com.linc.model.StoryModel
import com.linc.model.UserStoryModel

fun UserStoryEntity.toStoriesModel() = UserStoryModel(
    userId = userId,
    username = username,
    userAvatarUrl = userAvatarUrl,
    latestStoryTimestamp = latestStoryTimestamp,
    stories = stories.map(StoryEntity::toModel)
)

fun StoryEntity.toModel() = StoryModel(
    id = id,
    contentUrl = contentUrl,
    duration = duration,
    createdTimestamp = createdTimestamp,
    expiresTimestamp = expiresTimestamp
)
