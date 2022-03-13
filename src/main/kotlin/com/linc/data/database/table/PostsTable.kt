package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.util.*

object PostsTable : Table("posts") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val createdTimestamp: Column<DateTime> = datetime("created_at")
    val description: Column<String> = text("description")
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)
    val contentUrl: Column<String> = text("content_url")

}