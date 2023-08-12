package com.dev.demoapp.dev.extensions

fun Int?.orZero(): Int {
    return this ?: 0
}