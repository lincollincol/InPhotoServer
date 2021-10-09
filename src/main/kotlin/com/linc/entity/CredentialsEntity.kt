package com.linc.entity

import kotlinx.serialization.Serializable
import org.joda.time.DateTime
import java.util.*

data class CredentialsEntity(
    val id: UUID,
    val email: String,
    val password: String,
    val createdTimestamp: DateTime,
    val accessToken: String,
    val userId: UUID
)