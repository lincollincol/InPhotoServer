package com.linc.model.mappers

import com.linc.data.database.entity.story.StoryEntity
import com.linc.data.database.entity.story.UserStoryEntity
import com.linc.data.database.entity.user.UserEntity
import com.linc.model.StoryModel
import com.linc.model.UserStoryModel

fun UserEntity.toStoriesModel(stories: List<StoryEntity>) = UserStoryModel(
    userId = id,
    username = name,
    userAvatarUrl = avatarUrl,
    stories = stories.map(StoryEntity::toModel)
)

fun UserStoryEntity.toStoriesModel() = UserStoryModel(
    userId = userId,
    username = username,
    userAvatarUrl = userAvatarUrl,
    stories = stories.map(StoryEntity::toModel)
)

fun StoryEntity.toModel() = StoryModel(
    id = id,
    contentUrl = contentUrl,
    duration = duration,
    createdTimestamp = createdTimestamp,
    expiresTimestamp = expiresTimestamp
)
