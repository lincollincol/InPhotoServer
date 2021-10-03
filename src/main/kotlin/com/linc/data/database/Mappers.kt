package com.linc.data.database

import com.linc.data.database.table.AccountsTable
import com.linc.data.database.table.UsersTable
import com.linc.entity.AccountEntity
import com.linc.entity.UserEntity
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toAccountEntity() = AccountEntity(
    id = get(AccountsTable.id),
    email = get(AccountsTable.email),
    name = get(AccountsTable.name),
    password = get(AccountsTable.password),
    createdTimestamp = get(AccountsTable.createdTimestamp),
    accessToken = get(AccountsTable.accessToken),
    userId = get(AccountsTable.userId)
)

fun ResultRow.toUserEntity() = UserEntity(
    id = get(UsersTable.id),
    status = get(UsersTable.status),
    publicProfile = get(UsersTable.publicAccess),
    avatarId = get(UsersTable.avatarId)
)