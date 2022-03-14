package com.linc.data.database.mapper

import com.linc.data.database.entity.post.CommentEntity
import com.linc.data.database.table.CommentsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toCommentEntity() = CommentEntity(
    id = get(CommentsTable.id).toString(),
    text = get(CommentsTable.comment),
    username = get(UsersTable.name),
    userAvatarUrl = get(UsersTable.avatarUrl)
)