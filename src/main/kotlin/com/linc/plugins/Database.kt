package com.linc.plugins

import com.linc.data.database.DatabaseManager
import io.ktor.application.*
import org.koin.ktor.ext.inject

fun Application.configureDatabase() {

    val databaseManager: DatabaseManager by inject()
    databaseManager.init()
}