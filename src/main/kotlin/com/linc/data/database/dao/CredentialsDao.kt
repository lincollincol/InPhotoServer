package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import com.linc.data.database.toCredentialsEntity
import com.linc.data.dto.request.auth.SignUpRequestDTO
import com.linc.entity.CredentialsEntity
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

class CredentialsDao {

    suspend fun createAccount(
        userId: UUID,
        signUpRequestDTO: SignUpRequestDTO,
        token: String
    ) = SqlExecutor.executeQuery {
        CredentialsTable.insert {
            it[CredentialsTable.id] = UUID.randomUUID()
            it[CredentialsTable.email] = signUpRequestDTO.email
            it[CredentialsTable.password] = signUpRequestDTO.password
            it[CredentialsTable.createdTimestamp] = DateTime.now()
            it[CredentialsTable.accessToken] = token
            it[CredentialsTable.userId] = userId
        } get CredentialsTable.id
    }

    suspend fun getAccountByEmail(email: String) = SqlExecutor.executeQuery {
        CredentialsTable.select {
            CredentialsTable.email eq email
        }.firstOrNull()?.toCredentialsEntity()
//        collection?.toCredentialsEntity()
    }

    suspend fun getAccountById(accountId: UUID) = SqlExecutor.executeQuery {
        CredentialsTable.select {
            CredentialsTable.id eq accountId
        }.firstOrNull()?.toCredentialsEntity()
    }

}