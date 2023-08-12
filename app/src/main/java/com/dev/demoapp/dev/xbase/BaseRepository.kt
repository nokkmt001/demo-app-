package com.dev.demoapp.dev.xbase

import com.dev.demoapp.R
import com.dev.demoapp.dev.common.AppResource
import com.dev.demoapp.model.ErrorResponse
import com.dev.demoapp.model.RepoState
import com.dev.demoapp.model.UiState
import com.dev.demoapp.model.enum.RequestStatus
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

open class BaseRepository {

    private val TAG: String = this.javaClass.simpleName

    suspend fun <T : Any> makeApiCall(call: suspend () -> Response<T>): UiState<T> =
        try {
            val response = call.invoke()
            Timber.tag(TAG).i("makeApiCall: %s", response)
            Timber.tag(TAG).i("callSms: %s", response.message())
            Timber.tag(TAG).i("isSuccess: %s", response.isSuccessful)
            Timber.tag(TAG).i("callBody: %s", response.body())
            if (response.isSuccessful) {
                if (response.body() != null) {
                    UiState(RepoState.SUCCESS, response.body())
                } else {
                    UiState(RepoState.FAIL, null, "body null")
                }
            } else {
//                Timber.tag(TAG).i("errorBody: %s", Gson().fromJson(response.errorBody().toString(), ErrorResponse::class.java))
                val responseError = getMessageFromResponse(response)
                var errorMain: String? = ""
                errorMain = responseError
                if (responseError.isNullOrEmpty()) {
                    errorMain = response.message()
                }
                when {
                    response.code() == RequestStatus.NO_AUTHENTICATION.value -> {
                        UiState(
                            RepoState.FAIL,
                            null,
                            errorMain,
                            response.code()
                        )
                    }
                    response.code() == RequestStatus.BAD_REQUEST.value -> {
                        UiState(
                            RepoState.FAIL,
                            null,
                            errorMain,
                            response.code()
                        )

                    }

                    response.code() == RequestStatus.REQUEST_NOT_FOUND.value -> {
                        UiState(
                            RepoState.FAIL,
                            null,
                            AppResource.application.getString(R.string.error_not_found),
                            response.code()
                        )
                    }

                    else -> {
                        UiState(
                            RepoState.FAIL,
                            null,
                            AppResource.application.getString(R.string.error_unknown),
                            response.code()
                        )
                    }
                }
            }

        } catch (e: Exception) {
            UiState(RepoState.FAIL, null, e.message.toString())
        }

    suspend fun <T : Any> apiCall(call: suspend () -> Call<T>): UiState<T> {
        var result: UiState<T> = UiState<T>()
        try {
            call.invoke().enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Timber.tag(TAG).i("makeApiCall: %s", response)
                    Timber.tag(TAG).i("callSms: %s", response.message())
                    Timber.tag(TAG).i("isSuccess: %s", response.isSuccessful)
                    Timber.tag(TAG).i("callBody: %s", response.body())
                    result = if (response.isSuccessful) {
                        if (response.body() != null) {
                            UiState(RepoState.SUCCESS, response.body())
                        } else {
                            UiState(RepoState.FAIL, null, "body null")
                        }
                    } else {
                        var responseError = getMessageFromResponse(response)
                        Timber.tag(TAG).i("errorBody: %s", responseError)

                        var errorMain: String? = ""
                        errorMain = responseError
                        if (responseError.isNullOrEmpty()) {
                            errorMain = response.message()
                        }

                        UiState(RepoState.FAIL, null, errorMain)
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    result = UiState(RepoState.FAIL, null, t.message.toString())
                }
            })
        } catch (e: Exception) {
            result = UiState(RepoState.FAIL, null, e.message.toString())
        }
        return result
    }

    private fun <T> getMessageFromResponse(response: Response<T>): String? {
        var message: String? = ""
        val errorJson: String
        try {
            errorJson = response.errorBody()!!.string()
            val oroErrorResponse: ErrorResponse = Gson().fromJson(
                errorJson,
                ErrorResponse::class.java
            )
            message = oroErrorResponse.message
            if (message == null || message == "") message = response.message()
        } catch (ignored: java.lang.Exception) {
            Timber.tag(TAG).i("catchError: %s", ignored.message)
            message = ignored.message
        }
        return message
    }

}
