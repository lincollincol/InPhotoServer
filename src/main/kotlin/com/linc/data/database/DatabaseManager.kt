package com.linc.data.database

import com.linc.data.database.entity.TestEntity
import com.linc.data.database.entity.TestTable
import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseManager {

    //jdbc:postgresql://ec2-35-171-171-27.compute-1.amazonaws.com:5432/dff1v40rbobbrn

    companion object {
        private const val JDBC_URL = "jdbc:postgresql://ec2-35-171-171-27.compute-1.amazonaws.com:5432/dff1v40rbobbrn?password=774f00bbd13d916d8d64cabefe9ebb9ef5e8e2330dfef2c046f52f571befd198&sslmode=require&user=yswkdkfclyvpyk"
    }

    fun init() {
        Database.connect(hikari())
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig()
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = JDBC_URL
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

    suspend fun getAllUsers(): List<TestEntity> = dbQuery {
        TestTable.selectAll().map { toTestEntity(it) }
    }

    /*suspend fun getUserByEmail(email: String): TestEntity? = dbQuery {
        Users.select {
            (Users.email eq email)
        }.mapNotNull { toUser(it) }
            .singleOrNull()
    }*/

    private fun toTestEntity(row: ResultRow): TestEntity =
        TestEntity(
            id = row[TestTable.id],
            data = row[TestTable.email],
        )

}