package com.dev.demoapp.model

data class Folder (
    var name: String? = null,
    var path: String? = null,
    var cover: Image? = null,
    var images: ArrayList<Image> = ArrayList()
)

