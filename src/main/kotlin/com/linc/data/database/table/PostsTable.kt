package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object PostsTable : Table("posts") {

    val id: Column<UUID> = ContentsTable.uuid("id").primaryKey()
    val createdTimestamp: Column<Long> = long("created_at")
    val description: Column<String> = text("description")
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)
    val contentId: Column<UUID> = uuid("content_id").references(ContentsTable.id)

}