package com.dev.demoapp.network.repository

import com.dev.demoapp.dev.xbase.BaseRepository
import com.dev.demoapp.network.MainApi

class MainRepository constructor(
    private val mainApi: MainApi
) : BaseRepository() {

}
