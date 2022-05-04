package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object SystemContentTable : Table("system_content") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val dir: Column<String> = text("dir")
    val url: Column<String> = text("url").uniqueIndex()

}