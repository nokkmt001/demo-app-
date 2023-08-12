package com.dev.demoapp.model.enum

enum class RequestCode(val value: Int) {
    NO_REQUEST(-1),
    PERMISSION(0),
    REQUEST_IMAGE_PICKER(1),
    SCAN_QR(4),
    REQUEST_IMAGE(2)
}

enum class RequestLanguage(val value: Int) {
    VIETNAMESE(0),
    ENGLISH(1)
}

