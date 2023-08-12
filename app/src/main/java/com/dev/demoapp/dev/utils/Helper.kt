package com.dev.demoapp.dev.utils

import timber.log.Timber

fun doCatch(
success: () -> Unit,
error: ((Exception) -> Unit?)? = null,
className: String? = "BaseError"
) {
    try {
        success.invoke()
    } catch (e: Exception) {
        Timber.tag(className.toString()).i(e.toString())
        error?.invoke(e)
    }
}