package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toUserEntity
import com.linc.data.database.table.FollowersTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.*
import java.util.*

class FollowersDao {

    suspend fun followUser(
        userId: UUID,
        followerId: UUID
    ) = SqlExecutor.executeQuery {
        FollowersTable.insert { table ->
            table[FollowersTable.id] = UUID.randomUUID()
            table[FollowersTable.followedId] = userId
            table[FollowersTable.followerId] = followerId
        } get FollowersTable.id
    }

    suspend fun unfollowUser(
        userId: UUID,
        followerId: UUID
    ) = SqlExecutor.executeQuery {
        FollowersTable.deleteWhere {
            (FollowersTable.followedId eq userId) and (FollowersTable.followerId eq followerId)
        }
    }

    suspend fun getUserFollowers(userId: UUID) = SqlExecutor.executeQuery {
        FollowersTable.innerJoin(UsersTable, { followerId }, { id })
            .select { FollowersTable.followedId eq userId }
            .map(ResultRow::toUserEntity)
    }

    suspend fun getUserFollowersIds(userId: UUID) = SqlExecutor.executeQuery {
        FollowersTable.select { FollowersTable.followedId eq userId }
            .map { it[FollowersTable.followerId].toString() }
    }

    suspend fun getUserFollowing(userId: UUID) = SqlExecutor.executeQuery {
        FollowersTable.innerJoin(UsersTable, { followedId }, { id })
            .select { FollowersTable.followerId eq userId }
            .map(ResultRow::toUserEntity)
    }

    suspend fun getUserFollowingIds(userId: UUID) = SqlExecutor.executeQuery {
        FollowersTable.select { FollowersTable.followerId eq userId }
            .map { it[FollowersTable.followedId].toString() }
    }

    suspend fun getUserFollowersCount(userId: UUID) = SqlExecutor.executeQuery {
        FollowersTable.select { FollowersTable.followedId eq userId }
            .count()
    }

    suspend fun getUserFollowingsCount(userId: UUID) = SqlExecutor.executeQuery {
        FollowersTable.select { FollowersTable.followerId eq userId }
            .count()
    }

}