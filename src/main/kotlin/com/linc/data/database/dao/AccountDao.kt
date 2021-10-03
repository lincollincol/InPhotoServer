package com.linc.data.database.dao

import com.linc.data.database.SqlExecutor
import com.linc.data.database.table.AccountsTable
import com.linc.data.database.toAccountEntity
import com.linc.data.dto.request.auth.SignUpRequestDTO
import com.linc.utils.TokenGenerator
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.joda.time.DateTime
import java.util.*

class AccountDao(
    private val tokenGenerator: TokenGenerator
) {

    suspend fun createAccount(
        userId: UUID,
        signUpRequestDTO: SignUpRequestDTO
    ) = SqlExecutor.executeQuery {
        AccountsTable.insert {
            it[AccountsTable.id] = UUID.randomUUID()
            it[AccountsTable.name] = null
            it[AccountsTable.email] = signUpRequestDTO.email
            it[AccountsTable.password] = signUpRequestDTO.password
            it[AccountsTable.accessToken] = tokenGenerator.generateToken()
            it[AccountsTable.createdTimestamp] = DateTime.now()
            it[AccountsTable.userId] = userId
        } get AccountsTable.id
    }

    suspend fun getAccountById(accountId: UUID) = SqlExecutor.executeQuery {
        AccountsTable.select {
            AccountsTable.id eq accountId
        }.first().toAccountEntity()
    }

}