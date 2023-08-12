package com.dev.demoapp.network.repository

import com.dev.demoapp.dev.xbase.BaseRepository
import com.dev.demoapp.model.ChangeLog
import com.dev.demoapp.model.UiState
import com.dev.demoapp.model.UserLogin
import com.dev.demoapp.model.UserLoginPass
import com.dev.demoapp.model.UserResponse
import com.dev.demoapp.model.UserSignup
import com.dev.demoapp.model.UserSignupRequest
import com.dev.demoapp.network.MainApi

class UserRepository(private val mainApi: MainApi) : BaseRepository() {

    suspend fun getLogin(user: UserLoginPass): UiState<UserResponse> = makeApiCall {
        mainApi.loginAsync(user).await()
    }
    suspend fun signup(data: UserSignupRequest): UiState<UserSignup> = makeApiCall {
        mainApi.signupAsync(data).await()
    }

}
