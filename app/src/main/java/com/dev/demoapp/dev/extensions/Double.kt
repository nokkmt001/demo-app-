package com.dev.demoapp.dev.extensions

fun Double?.orZero(): Double {
    return this ?: 0.0
}