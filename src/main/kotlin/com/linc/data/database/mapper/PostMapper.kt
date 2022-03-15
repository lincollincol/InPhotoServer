package com.linc.data.database.mapper

import com.linc.data.database.entity.post.ExtendedPostEntity
import com.linc.data.database.entity.post.PostEntity
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

fun ResultRow.toExtendedPostEntity(
    likesCount: Int,
    commentsCount: Int,
    isLiked: Boolean,
    isBookmarked: Boolean,
    tags: List<String>
) = ExtendedPostEntity(
    id = get(PostsTable.id).toString(),
    createdTimestamp = get(PostsTable.createdTimestamp).millis,
    description = get(PostsTable.description),
    contentUrl = get(PostsTable.contentUrl),
    userId = get(PostsTable.userId).toString(),
    username = get(UsersTable.name),
    userAvatarUrl = get(UsersTable.avatarUrl),
    isLiked = isLiked,
    isBookmarked = isBookmarked,
    likesCount = likesCount,
    commentsCount = commentsCount,
    tags = tags
)
