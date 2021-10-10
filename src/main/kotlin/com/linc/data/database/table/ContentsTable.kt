package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.sql.Blob
import java.util.*

object ContentsTable : Table("contents") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val data: Column<ByteArray> = binary("data", 255)
    val extension: Column<String> = varchar("extension", 8)

}