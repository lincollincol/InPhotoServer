package com.linc.data.database.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table
import java.util.*

object FollowersTable : Table("followers") {

    val id: Column<UUID> = uuid("id").primaryKey()
    val followerId: Column<UUID> = (uuid("follower_id").references(UsersTable.id))
    val followedId: Column<UUID> = (uuid("followed_id").references(UsersTable.id))

}