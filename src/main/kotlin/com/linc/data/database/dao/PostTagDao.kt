package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toPostTagEntity
import com.linc.data.database.table.PostTagTable
import com.linc.data.database.table.TagsTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class PostTagDao {

    suspend fun createPostTag(
        tagId: UUID,
        postId: UUID
    ) = SqlExecutor.executeQuery {
        PostTagTable.insert { table ->
            table[PostTagTable.id] = UUID.randomUUID()
            table[PostTagTable.tagId] = tagId
            table[PostTagTable.postId] = postId
        } get PostTagTable.id
    }

    suspend fun getPostTags(postId: UUID) = SqlExecutor.executeQuery {
        PostTagTable.innerJoin(TagsTable)
            .select { PostTagTable.id eq postId }
            .map(ResultRow::toPostTagEntity)
    }

    suspend fun getPostTagByIdId(id: UUID) = SqlExecutor.executeQuery {
        PostTagTable.innerJoin(TagsTable)
            .select { PostTagTable.id eq id }
            .firstOrNull()
            ?.toPostTagEntity()
    }

    suspend fun getPostTagByTagId(tagId: UUID) = SqlExecutor.executeQuery {
        PostTagTable.innerJoin(TagsTable)
            .select { PostTagTable.tagId eq tagId }
            .firstOrNull()
            ?.toPostTagEntity()
    }

    suspend fun getPostTagByPostId(postId: UUID) = SqlExecutor.executeQuery {
        PostTagTable.innerJoin(TagsTable)
            .select { PostTagTable.postId eq postId }
            .firstOrNull()
            ?.toPostTagEntity()
    }

    suspend fun deletePostTagById(id: UUID) = SqlExecutor.executeQuery {
        PostTagTable.deleteWhere { PostTagTable.id eq id }
    }

    suspend fun deletePostTagByTagId(tagId: UUID) = SqlExecutor.executeQuery {
        PostTagTable.deleteWhere { PostTagTable.tagId eq tagId }
    }

    suspend fun deletePostTagByPostId(postId: UUID) = SqlExecutor.executeQuery {
        PostTagTable.deleteWhere { PostTagTable.postId eq postId }
    }

}