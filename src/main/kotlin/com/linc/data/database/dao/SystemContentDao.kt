package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toSystemContentEntity
import com.linc.data.database.table.SystemContentTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.select
import java.util.*

class SystemContentDao {

    suspend fun insertContentData(
        dir: String,
        url: String
    ) = SqlExecutor.executeQuery {
        SystemContentTable.insertIgnore { table ->
            table[SystemContentTable.id] = UUID.randomUUID()
            table[SystemContentTable.dir] = dir
            table[SystemContentTable.url] = url
        } get SystemContentTable.id
    }

    suspend fun getSystemContentById(
        id: UUID
    ) = SqlExecutor.executeQuery {
        SystemContentTable.select { SystemContentTable.id eq id }
            .singleOrNull()
            ?.toSystemContentEntity()
    }

    suspend fun getSystemContentByDir(
        dir: String
    ) = SqlExecutor.executeQuery {
        SystemContentTable.select { SystemContentTable.dir eq dir }
            .map(ResultRow::toSystemContentEntity)
    }

    suspend fun getRandomSystemContentByDir(
        dir: String
    ) = SqlExecutor.executeQuery {
        SystemContentTable.select { SystemContentTable.dir eq dir }
            .map(ResultRow::toSystemContentEntity)
            .random()
    }

}