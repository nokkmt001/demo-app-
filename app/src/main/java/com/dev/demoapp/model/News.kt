package com.dev.demoapp.model

data class News(
    var id: Int? = null,
    var title: String? = null,
    var content: String? = null,
    var like: Boolean? = false,
    var totalLike: Int? = null,
    var totalComment: Int? = null,
    var isBookMark: Boolean? = false,
    var userName: String? = null,
    var timeCreate: String? = null
)