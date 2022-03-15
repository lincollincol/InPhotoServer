package com.linc.utils.extensions

import com.linc.data.network.ContentManager
import io.ktor.http.content.*

suspend fun MultiPartData.getFileName() = getFileItem()?.originalFileName

suspend fun MultiPartData.getFileData() = getFileItem()?.streamProvider?.let { it() }

suspend fun MultiPartData.getFileItem(): PartData.FileItem? {
    var fileItem: PartData.FileItem? = null
    forEachPart { part ->
        if (part is PartData.FileItem) {
            fileItem = part
        }
    }
    return fileItem
}