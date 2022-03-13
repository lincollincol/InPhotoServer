package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.PostTagTable
import org.jetbrains.exposed.sql.insert
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

}