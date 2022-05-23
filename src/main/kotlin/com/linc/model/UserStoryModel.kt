package com.linc.model

data class UserStoryModel(
    val userId: String,
    val username: String,
    val userAvatarUrl: String,
    val stories: List<StoryModel>
)