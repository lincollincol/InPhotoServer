package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.mapper.toCredentialsEntity
import com.linc.data.database.table.CredentialsTable
import com.linc.data.network.dto.request.auth.SignUpDTO
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

class CredentialsDao {

    suspend fun createAccount(
        userId: UUID,
        signUpDTO: SignUpDTO,
        token: String
    ) = SqlExecutor.executeQuery {
        CredentialsTable.insert {
            it[CredentialsTable.id] = UUID.randomUUID()
            it[CredentialsTable.password] = signUpDTO.password
            it[CredentialsTable.createdTimestamp] = DateTime.now()
            it[CredentialsTable.accessToken] = token
            it[CredentialsTable.userId] = userId
        } get CredentialsTable.id
    }

    suspend fun getCredentialsByUserId(userId: UUID) = SqlExecutor.executeQuery {
        CredentialsTable.select {
            CredentialsTable.userId eq userId
        }.firstOrNull()?.toCredentialsEntity()
    }

    suspend fun getAccountById(accountId: UUID) = SqlExecutor.executeQuery {
        CredentialsTable.select {
            CredentialsTable.id eq accountId
        }.firstOrNull()?.toCredentialsEntity()
    }

}