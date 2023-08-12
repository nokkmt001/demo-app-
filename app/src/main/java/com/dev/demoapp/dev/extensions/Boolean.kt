package com.dev.demoapp.dev.extensions

fun Boolean?.orFalse(): Boolean {
    return this ?: false
}