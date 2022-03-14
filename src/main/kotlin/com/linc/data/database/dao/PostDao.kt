package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toExtendedPostEntity
import com.linc.data.database.mapper.toTagEntity
import com.linc.data.database.table.PostTagTable
import com.linc.data.database.table.PostsTable
import com.linc.data.database.table.TagsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.*
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

    suspend fun updatePost(
        postId: UUID,
        contentUrl: String,
        description: String
    ) = SqlExecutor.executeQuery {
        PostsTable.update(where = { PostsTable.id eq postId }) { table ->
            table[PostsTable.contentUrl] = contentUrl
            table[PostsTable.description] = description
        }
    }

    suspend fun getPostsByUserId(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .select { PostsTable.userId eq userId }
            .map { result ->
                val postTagId = result[PostsTable.id]
                val tags = PostTagTable.innerJoin(TagsTable)
                    .select { PostTagTable.postId eq postTagId }
                    .map(ResultRow::toTagEntity)
                result.toExtendedPostEntity(tags)
            }
    }

    suspend fun getPostById(postId: UUID) = SqlExecutor.executeQuery {
        val rawPost = PostsTable.innerJoin(UsersTable)
            .select { PostsTable.id eq postId }
            .firstOrNull()
        val tags = PostTagTable.innerJoin(TagsTable)
            .select { PostTagTable.postId eq postId }
            .map(ResultRow::toTagEntity)
        return@executeQuery rawPost?.toExtendedPostEntity(tags)
    }

    suspend fun deletePost(postId: UUID) = SqlExecutor.executeQuery {
        PostsTable.deleteWhere { PostsTable.id eq postId }
    }

}