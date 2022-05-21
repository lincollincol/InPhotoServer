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

    /*
    select * from posts
    inner join users author on posts.user_id = author.id
    inner join followers f on author.id = f.followed_id
    where f.follower_id = '3a886d5f-e7ca-4343-84f8-b129c3721f2f'
     */
    suspend fun getUserFollowingPosts(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable, { PostsTable.userId }, { UsersTable.id })
            .innerJoin(FollowersTable, { UsersTable.id }, { FollowersTable.followedId })
            .select { FollowersTable.followerId eq userId }
            .map(ResultRow::toPostEntity)
    }

    suspend fun getUserFollowingExtendedPosts(userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(UsersTable, { PostsTable.userId }, { UsersTable.id })
            .innerJoin(FollowersTable, { UsersTable.id }, { FollowersTable.followedId })
            .select { FollowersTable.followerId eq userId }
            .map { getExtendedPost(it, userId) }
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

    suspend fun getExtendedPostsByTag(tagId: UUID, userId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(PostTagTable)
            .innerJoin(UsersTable)
            .select { PostTagTable.tagId eq tagId }
            .map { getExtendedPost(it, userId) }
    }

    suspend fun getPostsByTag(tagId: UUID) = SqlExecutor.executeQuery {
        PostsTable.innerJoin(PostTagTable)
            .select { PostTagTable.tagId eq tagId }
            .map(ResultRow::toPostEntity)
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