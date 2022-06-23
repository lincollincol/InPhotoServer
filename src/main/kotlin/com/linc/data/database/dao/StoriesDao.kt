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

    suspend fun getUserStories(userId: UUID) = SqlExecutor.executeQuery {
        val result = StoriesTable.innerJoin(UsersTable)
            .select { StoriesTable.userId eq userId }
            .andWhere { StoriesTable.expireTimestamp greater DateTime.now() }
            .toList()

        if (result.isEmpty()) {
            return@executeQuery null
        }

        val latestStory = result.maxOf {
            it.get(StoriesTable.createdTimestamp)
        }
        result.firstOrNull()?.toUserStoryEntity(
            latestStory.millis,
            result
        )
    }

    suspend fun getUserFollowingStories(userId: UUID) = SqlExecutor.executeQuery {
        StoriesTable.innerJoin(UsersTable, { StoriesTable.userId }, { UsersTable.id })
            .innerJoin(FollowersTable, { UsersTable.id }, { FollowersTable.followedId })
            .select { FollowersTable.followerId eq userId }
            .andWhere { StoriesTable.expireTimestamp greater DateTime.now() }
            .groupBy { it.get(UsersTable.id) }
            .map { entry ->
                val latestStory = entry.value.maxOf {
                    it.get(StoriesTable.createdTimestamp)
                }
                entry.value.firstOrNull()?.toUserStoryEntity(
                    latestStory.millis,
                    entry.value
                )
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