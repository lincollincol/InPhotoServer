package com.linc.plugins

import com.linc.di.appModules
import io.ktor.application.*
import org.koin.ktor.ext.Koin
import org.koin.logger.SLF4JLogger

fun Application.configureDi() {
    // Init koin di
    Koin.install(this) {
        SLF4JLogger()
        modules(appModules)
    }
}