package com.linc.data.database.table


import com.linc.data.database.table.UsersTable.uniqueIndex
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.util.*

object CredentialsTable : Table("credentials") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val password: Column<String> = varchar("password", 16)
    val createdTimestamp: Column<DateTime> = datetime("created_at")
    val accessToken: Column<String> = text("access_token").uniqueIndex()
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)

}