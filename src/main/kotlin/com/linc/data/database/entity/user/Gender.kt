package com.linc.data.database.entity.user

enum class Gender {
    MALE, FEMALE, UNKNOWN;

    companion object {
        @JvmStatic
        fun fromString(gender: String?) = values().find {
            it.name.equals(gender, true)
        } ?: UNKNOWN
    }
}