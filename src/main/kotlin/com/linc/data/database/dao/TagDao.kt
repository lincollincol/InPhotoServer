package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toTagEntity
import com.linc.data.database.table.TagsTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class TagDao {

    suspend fun tagExist(tag: String) = SqlExecutor.executeQuery {
        return@executeQuery TagsTable.select { TagsTable.tag eq tag }.firstOrNull() != null
    }

    suspend fun getTagByName(tag: String) = SqlExecutor.executeQuery {
        return@executeQuery TagsTable.select { TagsTable.tag eq tag }
            .firstOrNull()
            ?.toTagEntity()
    }

    suspend fun getTagById(tagId: UUID) = SqlExecutor.executeQuery {
        return@executeQuery TagsTable.select { TagsTable.id eq tagId }
            .firstOrNull()
            ?.toTagEntity()
    }

    suspend fun createTag(
        tag: String
    ) = SqlExecutor.executeQuery {
        TagsTable.insert { table ->
            table[TagsTable.id] = UUID.randomUUID()
            table[TagsTable.tag] = tag
        } get TagsTable.id
    }

}