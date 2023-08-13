package com.dev.demoapp.network.repository

import com.dev.demoapp.dev.xbase.BaseRepository
import com.dev.demoapp.model.Categories
import com.dev.demoapp.model.UiState
import com.dev.demoapp.model.UserSignup
import com.dev.demoapp.model.UserSignupRequest
import com.dev.demoapp.network.MainApi

class MainRepository constructor(
    private val mainApi: MainApi
) : BaseRepository() {
    suspend fun getCategories(body: HashMap<String, Any>): UiState<List<Categories>> = makeApiCall {
        mainApi.getCategoriesAsync(body).await()
    }
}
