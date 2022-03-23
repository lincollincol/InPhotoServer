package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.entity.post.ExtendedPostEntity
import com.linc.data.database.mapper.toExtendedPostEntity
import com.linc.data.database.mapper.toPostEntity
import com.linc.data.database.table.*
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime
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
            table[PostsTable.createdTimestamp] = DateTime.now()
            table[PostsTable.contentUrl] = contentUrl
            table[PostsTable.description] = description
        } get PostsTable.id
    }

    suspend fun updatePost(
        postId: UUID,
        description: String
    ) = SqlExecutor.executeQuery {
        PostsTable.update(where = { PostsTable.id eq postId }) { table ->
            table[PostsTable.description] = description
        }
    }

    suspend fun getPostsByUserId(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .select { PostsTable.userId eq userId }
            .map(ResultRow::toPostEntity)
    }

    suspend fun getPosts() = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .selectAll()
            .map(ResultRow::toPostEntity)
    }

    suspend fun getPostByPostId(postId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .select { PostsTable.id eq postId }
            .firstOrNull()
            ?.toPostEntity()
    }

    suspend fun getExtendedPostsByUserId(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .select { PostsTable.userId eq userId }
            .map { getExtendedPost(it, userId) }
    }

    suspend fun getExtendedPosts(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .selectAll()
            .map { getExtendedPost(it, userId) }
    }

    suspend fun getExtendedPostByPostId(postId: UUID, userId: UUID) = SqlExecutor.executeQuery {
        val row = PostsTable.innerJoin(UsersTable)
            .select { PostsTable.id eq postId }
            .firstOrNull()
            ?: return@executeQuery null
        return@executeQuery getExtendedPost(row, userId)
    }

    suspend fun getExtendedPostByPostId2(postId: UUID, userId: UUID) = SqlExecutor.executeQuery {
        val row = PostsTable.fullJoin(UsersTable)
            .fullJoin(CommentsTable)
            .fullJoin(LikesTable)
            .fullJoin(BookmarksTable)
//            .select { PostsTable.id eq postId }
//            .firstOrNull()
        return@executeQuery null
//        return@executeQuery getExtendedPost(row, userId)
    }

    suspend fun deletePost(postId: UUID) = SqlExecutor.executeQuery {
        PostsTable.deleteWhere { PostsTable.id eq postId }
    }

    private fun getExtendedPost(row: ResultRow, userId: UUID): ExtendedPostEntity {
        val postId = row[PostsTable.id]
        val comments = CommentsTable.select { CommentsTable.postId eq postId }
        val likes = LikesTable.select { LikesTable.postId eq postId }
        val bookmarks = BookmarksTable.select { BookmarksTable.postId eq postId }
        val tags = PostTagTable.innerJoin(TagsTable)
            .select { PostTagTable.postId eq postId }
            .map { tagRow -> tagRow[TagsTable.tag] }

        return row.toExtendedPostEntity(
            likes.count(),
            comments.count(),
            likes.firstOrNull { it[LikesTable.userId] == userId } != null,
            bookmarks.firstOrNull { it[BookmarksTable.userId] == userId } != null,
            tags
        )
    }

}