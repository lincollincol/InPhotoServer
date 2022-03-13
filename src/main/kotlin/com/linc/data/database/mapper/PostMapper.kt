package com.linc.data.database.mapper

import com.linc.data.database.entity.PostEntity
import com.linc.data.database.entity.UserEntity
import com.linc.data.database.table.PostsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.ResultRow



fun ResultRow.toPostEntity() = PostEntity(
    id = get(PostsTable.id).toString(),
    createdTimestamp = get(PostsTable.createdTimestamp),
    description = get(PostsTable.description),
    contentUrl = get(PostsTable.contentUrl),
    userId = get(PostsTable.userId).toString()
)