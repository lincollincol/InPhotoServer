package com.linc.data.database.mapper

import com.linc.data.database.entity.ExtendedPostEntity
import com.linc.data.database.entity.PostEntity
import com.linc.data.database.table.PostsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toPostEntity() = PostEntity(
    id = get(PostsTable.id).toString(),
    createdTimestamp = get(PostsTable.createdTimestamp).millis,
    description = get(PostsTable.description),
    contentUrl = get(PostsTable.contentUrl),
    userId = get(PostsTable.userId).toString()
)

/*
    val id: String,
    val createdTimestamp: Long,
    val description: String,
    val contentUrl: String,
    val username: String,
    val userAvatarUrl: String?,
    val userId: String,
    val tagsCount: Int
 */
fun ResultRow.toExtendedPostEntity(
//    likesCount: Int,
    tagsCount: Int,
//    commentsCount: Int
) = ExtendedPostEntity(
    id = get(PostsTable.id).toString(),
    createdTimestamp = get(PostsTable.createdTimestamp).millis,
    description = get(PostsTable.description),
    contentUrl = get(PostsTable.contentUrl),
    userId = get(PostsTable.userId).toString(),
    username = get(UsersTable.name),
    userAvatarUrl = get(UsersTable.avatarUrl),
    tagsCount = tagsCount
)