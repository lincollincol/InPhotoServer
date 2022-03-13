package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toPostEntity
import com.linc.data.database.mapper.toUserEntity
import com.linc.data.database.mapper.toUserExtendedEntity
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.PostsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
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
            table[PostsTable.createdTimestamp] = System.currentTimeMillis()
            table[PostsTable.contentUrl] = contentUrl
            table[PostsTable.description] = description
        } get PostsTable.id
    }

    suspend fun getPostById(postId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(PostsTable)
            .select { PostsTable.id eq postId }
            .firstOrNull()
            ?.toPostEntity()
    }

}