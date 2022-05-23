package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toStoryEntity
import com.linc.data.database.mapper.toUserStoryEntity
import com.linc.data.database.table.FollowersTable
import com.linc.data.database.table.StoriesTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.*
import org.joda.time.DateTime
import java.util.*

class StoriesDao {

    suspend fun createUserStory(
        userId: UUID,
        contentUrl: String,
        durationMillis: Long,
        expiresTimestamp: Long,
    ) = SqlExecutor.executeQuery {
        StoriesTable.insert { table ->
            table[StoriesTable.id] = UUID.randomUUID()
            table[StoriesTable.userId] = userId
            table[StoriesTable.contentUrl] = contentUrl
            table[StoriesTable.createdTimestamp] = DateTime.now()
            table[StoriesTable.expireTimestamp] = DateTime(expiresTimestamp)
            table[StoriesTable.duration] = durationMillis
        } get StoriesTable.id
    }

    suspend fun getStories() = SqlExecutor.executeQuery {
        return@executeQuery StoriesTable.innerJoin(UsersTable)
            .selectAll()
            .map(ResultRow::toStoryEntity)
    }

    suspend fun getStoriesByUser(userId: UUID) = SqlExecutor.executeQuery {
        return@executeQuery StoriesTable.innerJoin(UsersTable)
            .select { StoriesTable.userId eq userId }
            .map(ResultRow::toStoryEntity)
    }

    suspend fun getUserFollowingStories(userId: UUID) = SqlExecutor.executeQuery {
        StoriesTable.innerJoin(UsersTable, { StoriesTable.userId }, { UsersTable.id })
            .innerJoin(FollowersTable, { UsersTable.id }, { FollowersTable.followedId })
            .select { FollowersTable.followerId eq userId }
            .groupBy { it.get(UsersTable.id) }
            .map {
                it.value.firstOrNull()?.toUserStoryEntity(it.value)
            }
            .filterNotNull()
    }

//    suspend fun getUserFollowingStories(userId: UUID) = SqlExecutor.executeQuery {
//        StoriesTable.innerJoin(UsersTable, { StoriesTable.userId }, { UsersTable.id })
//            .innerJoin(FollowersTable, { UsersTable.id }, { FollowersTable.followedId })
//            .select { FollowersTable.followerId eq userId }
//            .map(ResultRow::toStoryEntity)
//    }

    suspend fun getStoryById(tagId: UUID) = SqlExecutor.executeQuery {
        return@executeQuery StoriesTable.select { StoriesTable.id eq tagId }
            .firstOrNull()
            ?.toStoryEntity()
    }

}