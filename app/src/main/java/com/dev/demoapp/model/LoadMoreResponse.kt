package com.dev.demoapp.model

data class LoadMoreResponse<T>(
        val data: List<T>? = null,
        val isLimitedData: Boolean = false
)
