package com.linc.utils.extensions

import java.util.*

fun String.toUUID() = UUID.fromString(this)

val String.Companion.EMPTY get() = ""


fun String.removeQuotes() = replace("\"", "")

fun String.removeSqlEscapes() =
    replace("\\n", "\n")
        .replace("\\r", "\r")
        .replace("\\t", "\t")
        .replace("\\", "")

fun String.extractStringBody() = drop(1).dropLast(1)