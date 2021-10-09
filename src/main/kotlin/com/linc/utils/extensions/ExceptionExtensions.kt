package com.linc.utils.extensions

import com.linc.utils.Constants.UNKNOWN_ERROR

fun Throwable.errorMessage(
    defaultMessage: String = UNKNOWN_ERROR
): String {
    return message ?: defaultMessage
}