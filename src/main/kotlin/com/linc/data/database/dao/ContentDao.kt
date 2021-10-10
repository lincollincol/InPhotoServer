package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.ContentsTable
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.toCredentialsEntity
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class ContentDao {

    suspend fun uploadContent(data: ByteArray, extension: String) = SqlExecutor.executeQuery {
        ContentsTable.insert {
            it[ContentsTable.id] = UUID.randomUUID()
            it[ContentsTable.data] = data
            it[ContentsTable.extension] = extension
        } get ContentsTable.id
    }

}