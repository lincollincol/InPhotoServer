package com.linc.plugins

import com.linc.data.repository.MediaRepository
import io.ktor.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject

fun Application.synchronizeLocalData() {
    val mediaRepository: MediaRepository by inject()
    CoroutineScope(Dispatchers.IO).launch {
        mediaRepository.syncWithRemoteSystemDirs()
    }
}