package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.LikesTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.update
import java.util.*

class LikeDao {

    suspend fun createLike(
        postId: UUID,
        userId: UUID
    ) = SqlExecutor.executeQuery {
        /*val likeResultRow = LikesTable.select {
            (LikesTable.postId eq postId) and (LikesTable.userId eq userId)
        }.firstOrNull()

        if (likeResultRow != null) {
            return@executeQuery likeResultRow[LikesTable.id]
        }*/

        try {
            LikesTable.insert { table ->
                table[LikesTable.id] = UUID.randomUUID()
                table[LikesTable.postId] = postId
                table[LikesTable.userId] = userId
            } get LikesTable.id
        } catch (e: Exception) {
            LikesTable.update(where = { (LikesTable.postId eq postId) and (LikesTable.userId eq userId) }) { table ->
                table[LikesTable.id] = UUID.randomUUID()
                table[LikesTable.postId] = postId
                table[LikesTable.userId] = userId
            }
        }
    }

    suspend fun deleteLike(
        postId: UUID,
        userId: UUID
    ) = SqlExecutor.executeQuery {
        LikesTable.deleteWhere {
            (LikesTable.postId eq postId) and (LikesTable.userId eq userId)
        }
    }

    suspend fun deleteLikes(postId: UUID) = SqlExecutor.executeQuery {
        LikesTable.deleteWhere { LikesTable.postId eq postId }
    }

}