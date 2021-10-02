package com.linc.data.database.entity

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object TestTable : Table("test_table") {
    val id: Column<Int> = integer("id").autoIncrement().primaryKey()
    val email: Column<String> = varchar("data", 100)
}

data class TestEntity(
    val id: Int,
    val data: String
)