package com.dev.demoapp.model

import androidx.annotation.Keep

@Keep
data class ApiResponse<T>(
    var message: String? = null,
    var statusCode: Int? = null,
    var value: T? = null,
    var msg : String? = null
)
