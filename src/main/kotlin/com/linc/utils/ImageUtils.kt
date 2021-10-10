package com.linc.utils

import com.linc.utils.extensions.randomUUID
import net.coobird.thumbnailator.Thumbnails
import java.io.File
import java.util.*

class ImageUtils {

    fun compressImage(image: File, compress: CompressType) : File {
        val thumb = File("${randomUUID()}.${image.extension}")
        val width = compress.size.first
        val height = compress.size.second

        Thumbnails.of(image)
            .size(width, height)
            .toFile(thumb)

        return thumb
    }

    enum class CompressType(val size: Pair<Int, Int>) {
        STANDARD_IMAGE(1080 to 1080),

        AVATAR_LARGE_THUMB(320 to 320),
        AVATAR_MEDIUM_THUMB(160 to 160),
        AVATAR_SMALL_THUMB(80 to 80),

        POST_THUMB(161 to 161)
    }

}