package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toCommentEntity
import com.linc.data.database.table.CommentsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.*
import java.util.*

class CommentDao {

    suspend fun getComments(postId: UUID) = SqlExecutor.executeQuery {
        CommentsTable.innerJoin(UsersTable)
            .select { CommentsTable.postId eq postId }
            .map(ResultRow::toCommentEntity)
    }

    suspend fun createComment(
        postId: UUID,
        userId: UUID,
        comment: String
    ) = SqlExecutor.executeQuery {
        CommentsTable.insert { table ->
            table[CommentsTable.id] = UUID.randomUUID()
            table[CommentsTable.userId] = userId
            table[CommentsTable.postId] = postId
            table[CommentsTable.comment] = comment
        } get CommentsTable.id
    }

    suspend fun updateComment(
        commentId: UUID,
        comment: String
    ) = SqlExecutor.executeQuery {
        CommentsTable.update(where = { CommentsTable.id eq commentId }) { table ->
            table[CommentsTable.comment] = comment
        }
    }

    suspend fun deleteComment(commentId: UUID) = SqlExecutor.executeQuery {
        CommentsTable.deleteWhere { CommentsTable.id eq commentId }
    }
}