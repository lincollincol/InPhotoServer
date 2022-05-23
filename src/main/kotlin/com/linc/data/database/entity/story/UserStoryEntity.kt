package com.linc.data.database.entity.story

data class UserStoryEntity(
    val userId: String,
    val username: String,
    val userAvatarUrl: String,
    val stories: List<StoryEntity>
)