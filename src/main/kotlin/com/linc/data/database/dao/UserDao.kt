package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toUserEntity
import com.linc.data.database.mapper.toUserExtendedEntity
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.utils.extensions.EMPTY
import org.jetbrains.exposed.sql.*
import java.util.*

class UserDao {

    suspend fun createEmptyUser(
        email: String,
        name: String,
        gender: String/*,
        avatarUrl: String,
        headerUrl: String*/
    ) = SqlExecutor.executeQuery {
        UsersTable.insert { table ->
            table[UsersTable.id] = UUID.randomUUID()
            table[UsersTable.name] = name
            table[UsersTable.email] = email
            table[UsersTable.status] = null
            table[UsersTable.gender] = gender
            table[UsersTable.avatarUrl] = String.EMPTY
            table[UsersTable.headerUrl] = String.EMPTY
            table[UsersTable.publicAccess] = true
        } get UsersTable.id
    }

    suspend fun userWithNameExist(
        username: String
    ) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.name eq username }
            .singleOrNull()
    }

    suspend fun getUsers() = SqlExecutor.executeQuery {
        UsersTable.selectAll().map { it.toUserEntity() }
    }

    suspend fun getExtendedUserById(userId: UUID) = SqlExecutor.executeQuery {
        CredentialsTable.innerJoin(UsersTable)
            .select { UsersTable.id eq userId }
            .firstOrNull()
            ?.toUserExtendedEntity()
    }

    suspend fun getUserByEmail(email: String) = SqlExecutor.executeQuery {
        UsersTable.innerJoin(CredentialsTable)
            .select { UsersTable.email eq email }
            .firstOrNull()
            ?.toUserExtendedEntity()
    }

    suspend fun getUserByName(name: String) = SqlExecutor.executeQuery {
        UsersTable.innerJoin(CredentialsTable)
            .select { UsersTable.name eq name }
            .firstOrNull()
            ?.toUserExtendedEntity()
    }

    suspend fun getUserAvatar(userId: UUID) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.id eq userId }
            .firstOrNull()
            ?.toUserEntity()?.avatarUrl
    }

    suspend fun getUserById(userId: UUID) = SqlExecutor.executeQuery {
        UsersTable.select { UsersTable.id eq userId }
            .firstOrNull()
            ?.toUserEntity()
    }

    suspend fun updateUserName(
        userId: UUID,
        name: String
    ) = updateUserField(userId, UsersTable.name, name)

    suspend fun updateUserStatus(
        userId: UUID,
        status: String
    ) = updateUserField(userId, UsersTable.status, status)

    suspend fun updateUserVisibility(
        userId: UUID,
        isPublic: Boolean
    ) = updateUserField(userId, UsersTable.publicAccess, isPublic)

    suspend fun updateUserGender(
        userId: UUID,
        gender: String
    ) = updateUserField(userId, UsersTable.gender, gender)

    suspend fun updateUserAvatar(
        userId: UUID,
        avatarUrl: String
    ) = updateUserField(userId, UsersTable.avatarUrl, avatarUrl)

    suspend fun updateUserHeader(
        userId: UUID,
        headerUrl: String
    ) = updateUserField(userId, UsersTable.headerUrl, headerUrl)

    /**
     * Private api
     */
    private suspend fun <F> updateUserField(
        userId: UUID,
        field: Column<F>,
        fieldData: F
    ) = SqlExecutor.executeQuery {
        UsersTable.update({ UsersTable.id eq userId }) { table ->
            table[field] = fieldData
        }
    }

}