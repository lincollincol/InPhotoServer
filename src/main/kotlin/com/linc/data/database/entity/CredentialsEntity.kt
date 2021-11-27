package com.linc.data.database.entity

import org.joda.time.DateTime
import java.util.*

data class CredentialsEntity(
    val id: UUID,
    val password: String,
    val createdTimestamp: DateTime,
    val accessToken: String,
    val userId: UUID
)