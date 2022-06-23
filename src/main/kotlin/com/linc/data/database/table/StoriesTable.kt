package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.util.*

object StoriesTable : Table("story") {
    val id: Column<UUID> = uuid("id").primaryKey()
    val contentUrl: Column<String> = text("content_url")
    val duration: Column<Long> = long("duration")
    val createdTimestamp: Column<DateTime> = datetime("created_at")
    val expireTimestamp: Column<DateTime> = datetime("expire_at")
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)
}