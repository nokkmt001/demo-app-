package com.dev.demoapp.dev.extensions

fun Long?.orZero(): Long {
    return this ?: 0L
}