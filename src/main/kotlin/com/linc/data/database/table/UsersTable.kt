package com.linc.data.database.table


import com.linc.entity.AccountEntity
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import java.util.*

object UsersTable : Table("users") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val status: Column<String?> = varchar("status", 128).nullable()
    val publicAccess: Column<Boolean> = bool("public_access").default(true)
    val avatarId: Column<UUID?> = uuid("avatar_id").references(ContentsTable.id).nullable()

    fun insertEntity(entity: AccountEntity) {
        insert {

        }
    }

}