package com.linc.data.database.mapper

import com.linc.data.database.entity.UserEntity
import com.linc.data.database.entity.UserExtendedEntity
import com.linc.data.database.table.CredentialsTable
import com.linc.data.database.table.UsersTable
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toUserEntity() = UserEntity(
    id = get(UsersTable.id).toString(),
    name = get(UsersTable.name),
    email = get(UsersTable.email),
    status = get(UsersTable.status),
    publicProfile = get(UsersTable.publicAccess),
    avatarUrl = get(UsersTable.avatarUrl)
)

fun ResultRow.toUserExtendedEntity() = UserExtendedEntity(
    id = get(UsersTable.id).toString(),
    name = get(UsersTable.name),
    email = get(UsersTable.email),
    status = get(UsersTable.status),
    publicProfile = get(UsersTable.publicAccess),
    accessToken = get(CredentialsTable.accessToken),
    avatarUrl = get(UsersTable.avatarUrl)
)