package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toExtendedPostEntity
import com.linc.data.database.mapper.toPostEntity
import com.linc.data.database.table.PostTagTable
import com.linc.data.database.table.PostsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

class PostDao {

    suspend fun createPost(
        userId: UUID,
        contentUrl: String,
        description: String
    ) = SqlExecutor.executeQuery {
        PostsTable.insert { table ->
            table[PostsTable.id] = UUID.randomUUID()
            table[PostsTable.userId] = userId
            table[PostsTable.createdTimestamp] = DateTime.now()
            table[PostsTable.contentUrl] = contentUrl
            table[PostsTable.description] = description
        } get PostsTable.id
    }

    suspend fun getPostsByUserId(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .select { PostsTable.userId eq userId }
            .map { result ->
                val postId = result[PostsTable.id]
                val tagsCount = PostTagTable.select { PostTagTable.postId eq postId }.count()
                result.toExtendedPostEntity(tagsCount)
            }
    }

    suspend fun getPostById(postId: UUID) = SqlExecutor.executeQuery {
        PostsTable.select { PostsTable.id eq postId }
            .firstOrNull()
            ?.toPostEntity()
    }

}