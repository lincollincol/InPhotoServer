package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.BookmarksTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class BookmarkDao {

    suspend fun createBookmark(
        postId: UUID,
        userId: UUID
    ) = SqlExecutor.executeQuery {
        val bookmarkResultRow = BookmarksTable.select {
            (BookmarksTable.postId eq postId) and (BookmarksTable.userId eq userId)
        }.firstOrNull()

        if (bookmarkResultRow != null) {
            return@executeQuery bookmarkResultRow[BookmarksTable.id]
        }

        BookmarksTable.insert { table ->
            table[BookmarksTable.id] = UUID.randomUUID()
            table[BookmarksTable.postId] = postId
            table[BookmarksTable.userId] = userId
        } get BookmarksTable.id
    }

    suspend fun deleteBookmark(
        postId: UUID,
        userId: UUID
    ) = SqlExecutor.executeQuery {
        BookmarksTable.deleteWhere {
            (BookmarksTable.postId eq postId) and (BookmarksTable.userId eq userId)
        }
    }

}