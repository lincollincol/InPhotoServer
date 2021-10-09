package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.database.toUserEntity
import com.linc.data.database.toUserExtendedEntity
import org.jetbrains.exposed.sql.*
import java.util.*

class UserDao {

    suspend fun createEmptyUser() = SqlExecutor.executeQuery {
        UsersTable.insert { table ->
            table[UsersTable.id] = UUID.randomUUID()
            table[UsersTable.name] = null
            table[UsersTable.status] = null
            table[UsersTable.avatarId] = null
            table[UsersTable.publicAccess] = true
        } get UsersTable.id
    }

    suspend fun getExtendedUserById(userId: UUID) = SqlExecutor.executeQuery {
        CredentialsTable.innerJoin(UsersTable)
            .select {
                UsersTable.id eq userId
            }.firstOrNull()?.toUserExtendedEntity()
    }

    suspend fun getUserById(userId: UUID) = SqlExecutor.executeQuery {
        UsersTable.select {
            UsersTable.id eq userId
        }.firstOrNull()?.toUserEntity()
    }

    suspend fun updateUserName(
        userId: UUID,
        name: String
    ) = updateUserField(userId, UsersTable.name, name)

    suspend fun updateUserStatus(
        userId: UUID,
        status: String
    ) = updateUserField(userId, UsersTable.status, status)


    /**
     * Private api
     */
    private suspend fun <F> updateUserField(
        userId: UUID,
        field: Column<F>,
        fieldData: F
    ) = SqlExecutor.executeQuery {
        UsersTable.update({ UsersTable.id eq  userId}) { table ->
            table[field] = fieldData
        }
    }

}