package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime
import java.util.*

object CommentsTable : Table("comments") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val comment: Column<String> = text("text")
    val createdTimestamp: Column<DateTime> = datetime("created_at")
    val userId: Column<UUID> = (uuid("user_id").references(UsersTable.id))
    val postId: Column<UUID> = (uuid("post_id").references(PostsTable.id))

}