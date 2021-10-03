package com.linc.data.database.table


import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.util.*

object AccountsTable : Table("accounts") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val email: Column<String> = varchar("email", 32)
    val name: Column<String?> = varchar("name", 32).uniqueIndex().nullable()
    val password: Column<String> = varchar("password", 16)
    val createdTimestamp: Column<DateTime> = datetime("created_at")
    val accessToken: Column<String> = varchar("access_token", 255).uniqueIndex()
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)

}