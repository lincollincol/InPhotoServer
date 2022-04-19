package com.linc.data.database.table


import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UsersTable : Table("users") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val name: Column<String> = text("username").uniqueIndex()
    val email: Column<String> = text("email").uniqueIndex()
    val status: Column<String?> = text("status").nullable()
    val publicAccess: Column<Boolean> = bool("is_public").default(true)
    val avatarUrl: Column<String?> = text("avatar_url").nullable()

}