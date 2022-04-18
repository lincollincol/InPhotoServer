package com.linc.utils.extensions

import java.util.*

fun String.toUUID() = UUID.fromString(this)

fun String.removeQuotes() = replace("\"", "")