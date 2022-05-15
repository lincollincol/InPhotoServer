package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.ChatsTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class ChatsDao {

    suspend fun createChat() = SqlExecutor.executeQuery {
        ChatsTable.insert { table ->
            table[ChatsTable.id] = UUID.randomUUID()
        } get ChatsTable.id
    }

    suspend fun getChatById(chatId: UUID) = SqlExecutor.executeQuery {
        ChatsTable.select { ChatsTable.id eq chatId }
            .singleOrNull()
    }

}
