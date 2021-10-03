package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.AccountsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.database.toAccountEntity
import com.linc.data.database.toUserEntity
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import java.util.*

class UserDao {

    suspend fun createUser() = SqlExecutor.executeQuery {
        UsersTable.insert { table ->
            table[UsersTable.id] = UUID.randomUUID()
            table[UsersTable.status] = null
            table[UsersTable.avatarId] = null
            table[UsersTable.publicAccess] = true
        } get UsersTable.id
    }

    suspend fun getUserById(userId: UUID) = SqlExecutor.executeQuery {
        UsersTable.select {
            UsersTable.id eq userId
        }.first().toUserEntity()
    }

}