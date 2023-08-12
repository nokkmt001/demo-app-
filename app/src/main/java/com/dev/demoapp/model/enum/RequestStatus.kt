package com.dev.demoapp.model.enum

enum class RequestStatus(val value: Int) {
    NO_INTERNET_CONNECTION(470),
    NO_AUTHENTICATION(401),
    BAD_GATEWAY(502),
    INTERNAL_SERVER(500),
    ERROR_CLIENT(472),
    BAD_REQUEST(400),
    REQUEST_DUPLICATION(409),
    REQUEST_NOT_FOUND(404),
    NO_CONTENT(204),
    SUCCESS(200)
}
