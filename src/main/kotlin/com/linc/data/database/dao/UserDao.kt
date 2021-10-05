package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.database.toUserEntity
import com.linc.data.database.toUserExtendedEntity
import com.linc.utils.TokenGenerator
import org.jetbrains.exposed.sql.ColumnSet
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import java.util.*

class UserDao(
    private val tokenGenerator: TokenGenerator
) {

    suspend fun createUser() = SqlExecutor.executeQuery {
        UsersTable.insert { table ->
            table[UsersTable.id] = UUID.randomUUID()
            table[UsersTable.name] = null
            table[UsersTable.status] = null
            table[UsersTable.avatarId] = null
            table[UsersTable.publicAccess] = true
            table[UsersTable.accessToken] = tokenGenerator.generateToken()
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

}