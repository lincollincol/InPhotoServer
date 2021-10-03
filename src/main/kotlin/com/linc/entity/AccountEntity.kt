package com.linc.entity

import kotlinx.serialization.Serializable
import org.joda.time.DateTime
import java.util.*

data class AccountEntity(
    val id: UUID,
    val email: String,
    val name: String?,
    val password: String,
    val createdTimestamp: DateTime,
    val accessToken: String,
    val userId: UUID
)