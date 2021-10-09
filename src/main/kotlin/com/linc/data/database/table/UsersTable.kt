package com.linc.data.database.table


import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UsersTable : Table("users") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val name: Column<String?> = varchar("name", 32).uniqueIndex().nullable()
    val status: Column<String?> = text("status").nullable()
    val publicAccess: Column<Boolean> = bool("is_public").default(true)
    val avatarId: Column<UUID?> = uuid("avatar_id").references(ContentsTable.id).nullable()

}