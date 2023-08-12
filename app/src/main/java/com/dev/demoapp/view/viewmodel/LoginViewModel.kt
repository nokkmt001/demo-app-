package com.dev.demoapp.view.viewmodel

import com.dev.demoapp.dev.xbase.BaseMvvmViewModel
import com.dev.demoapp.dev.xbase.sendValue
import com.dev.demoapp.model.*
import com.dev.demoapp.network.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import java.lang.Exception

class LoginViewModel constructor(
    private val response: UserRepository
) : BaseMvvmViewModel() {

    init {
        Timber.d("injection LoginViewModel")
    }

    private var _eventData = MutableStateFlow(UiState<UserResponse>())

    var eventData: StateFlow<UiState<UserResponse>> = _eventData

    private var _eventDataSignup = MutableStateFlow(UiState<UserSignup>())

    var eventDataSignup: StateFlow<UiState<UserSignup>> = _eventDataSignup

    fun checkLogin(userLoginPass: UserLoginPass) {
        ioScope.start {
            response.getLogin(userLoginPass).handleResponse {
                _eventData.sendValue(it)
            }
        }
    }

    fun signUp(userLoginPass: UserSignupRequest) {
        ioScope.start {
            response.signup(userLoginPass).handleResponse {
                _eventDataSignup.sendValue(it)
            }
        }
    }

}
