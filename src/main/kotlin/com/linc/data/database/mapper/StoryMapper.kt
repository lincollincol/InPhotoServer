package com.linc.data.database.mapper

import com.linc.data.database.entity.story.StoryEntity
import com.linc.data.database.entity.story.UserStoryEntity
import com.linc.data.database.table.StoriesTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUserStoryEntity(
    latestStoryTimestamp: Long,
    stories: List<ResultRow>,
) = UserStoryEntity(
    userId = get(UsersTable.id).toString(),
    username = get(UsersTable.name),
    userAvatarUrl = get(UsersTable.avatarUrl),
    latestStoryTimestamp = latestStoryTimestamp,
    stories = stories.map(ResultRow::toStoryEntity)
)

fun ResultRow.toStoryEntity() = StoryEntity(
    id = get(StoriesTable.id).toString(),
    contentUrl = get(StoriesTable.contentUrl),
    duration = get(StoriesTable.duration),
    createdTimestamp = get(StoriesTable.createdTimestamp).millis,
    expiresTimestamp = get(StoriesTable.expireTimestamp).millis
)

//fun ResultRow.toUserStoryEntity() = UserStoryEntity(
//    id = get(StoriesTable.id).toString(),
//    userId = get(StoriesTable.userId).toString(),
//    username = get(UsersTable.name),
//    userAvatarUrl = get(UsersTable.avatarUrl),
//    contentUrl = get(StoriesTable.contentUrl),
//    duration = get(StoriesTable.duration),
//    createdTimestamp = get(StoriesTable.createdTimestamp).millis,
//    expiresTimestamp = get(StoriesTable.expireTimestamp).millis
//)