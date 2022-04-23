package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object UserTagTable : Table("user_tag") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val tagId: Column<UUID> = uuid("tag_id").references(TagsTable.id)
    val userId: Column<UUID> = uuid("user_id").references(UsersTable.id)

}