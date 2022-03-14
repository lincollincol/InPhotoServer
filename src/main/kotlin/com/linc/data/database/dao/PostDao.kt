package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toExtendedPostEntity
import com.linc.data.database.mapper.toTagEntity
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
        contentUrl: String,
        description: String
    ) = SqlExecutor.executeQuery {
        PostsTable.update(where = { PostsTable.id eq postId }) { table ->
            table[PostsTable.contentUrl] = contentUrl
            table[PostsTable.description] = description
        }
    }

    suspend fun getPostsByUserId(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .select { PostsTable.userId eq userId }
            .map { result ->
                val postId = result[PostsTable.id]

                val comments = CommentsTable.select { CommentsTable.postId eq postId }
                val likes = LikesTable.select { LikesTable.postId eq postId }
                val bookmarks = BookmarksTable.select { BookmarksTable.postId eq postId }
                val tags = PostTagTable.innerJoin(TagsTable)
                    .select { PostTagTable.postId eq postId }
                    .map(ResultRow::toTagEntity)

                result.toExtendedPostEntity(
                    likes.count(),
                    comments.count(),
                    likes.firstOrNull { it[LikesTable.userId] == userId } != null,
                    bookmarks.firstOrNull { it[BookmarksTable.userId] == userId } != null,
                    tags
                )
            }
    }

    suspend fun getPostsByPostId(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable)
            .selectAll()
            .map { result ->
                val postId = result[PostsTable.id]

                val comments = CommentsTable.select { CommentsTable.postId eq postId }
                val likes = LikesTable.select { LikesTable.postId eq postId }
                val bookmarks = BookmarksTable.select { BookmarksTable.postId eq postId }
                val tags = PostTagTable.innerJoin(TagsTable)
                    .select { PostTagTable.postId eq postId }
                    .map(ResultRow::toTagEntity)

                result.toExtendedPostEntity(
                    likes.count(),
                    comments.count(),
                    likes.firstOrNull { it[LikesTable.userId] == userId } != null,
                    bookmarks.firstOrNull { it[BookmarksTable.userId] == userId } != null,
                    tags
                )
            }
    }

    suspend fun getPostByPostId(postId: UUID, userId: UUID) = SqlExecutor.executeQuery {
        val result = PostsTable.innerJoin(UsersTable)
            .select { PostsTable.id eq postId }
            .firstOrNull()

        val comments = CommentsTable.select { CommentsTable.postId eq postId }
        val likes = LikesTable.select { LikesTable.postId eq postId }
        val bookmarks = BookmarksTable.select { BookmarksTable.postId eq postId }
        val tags = PostTagTable.innerJoin(TagsTable)
            .select { PostTagTable.postId eq postId }
            .map(ResultRow::toTagEntity)

        result?.toExtendedPostEntity(
            likes.count(),
            comments.count(),
            likes.firstOrNull { it[LikesTable.userId] == userId } != null,
            bookmarks.firstOrNull { it[BookmarksTable.userId] == userId } != null,
            tags
        )
    }

    suspend fun deletePost(postId: UUID) = SqlExecutor.executeQuery {
        PostsTable.deleteWhere { PostsTable.id eq postId }
    }

}