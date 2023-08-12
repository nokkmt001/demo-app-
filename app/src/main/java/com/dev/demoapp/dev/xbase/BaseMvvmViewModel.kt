package com.dev.demoapp.dev.xbase

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.dev.demoapp.dev.extensions.lifecycle.LifeRegistry
import com.dev.demoapp.dev.utils.ContainsUtils
import com.dev.demoapp.model.RepoState
import com.dev.demoapp.model.UiState

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.await
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeoutException

fun <T> MutableStateFlow<T>.sendValue(value: T) {
    this.value = value
}

abstract class BaseMvvmViewModel : ViewModel(), LifecycleOwner {

    @SuppressLint("StaticFieldLeak")
    private val mLife = LifeRegistry(this)

    override fun getLifecycle() = mLife

    init {
        mLife.create().start()

    }

    protected var viewModelJob = Job()
    protected val ioContext = viewModelJob + Dispatchers.IO // background context
    protected val uiContext = viewModelJob + Dispatchers.Main // ui context
    protected val ioScope = CoroutineScope(ioContext)
    protected val uiScope = CoroutineScope(uiContext)

    var _eventLoading = MutableStateFlow(false)
    var _eventErrorMessage = MutableStateFlow(String())
    var _eventNoData = MutableStateFlow(false)

    var eventLoading: StateFlow<Boolean> = _eventLoading
    var eventErrorMessage: StateFlow<String> = _eventErrorMessage
    var eventNoData: StateFlow<Boolean> = _eventNoData

    var isRefreshOrLoadMore: Boolean = false
    var isMultiLoading: Boolean = false
    var isMultiCall: Boolean = false

    protected open fun getHeaders(token: String?): HashMap<String, Any> {
        val headers = HashMap<String, Any>()
        if (token != null && ContainsUtils.HEADER_HEAD_TOKEN.isNotEmpty())
            headers[ContainsUtils.HEADER_AUTHORIZATION] = token
        headers[ContainsUtils.HEADER_CONTENT_TYPE] = ContainsUtils.HEADER_CONTENT_TYPE_VALUE_JSON
        if (ContainsUtils.isMultiLanguage) headers[ContainsUtils.HEADER_LANG] =
            ContainsUtils.DEFAULT_LANGUAGE
        headers[ContainsUtils.HEADER_API_KEY] = ""
        headers[ContainsUtils.HEADER_UUID] = UUID.randomUUID()
        return headers
    }

    fun CoroutineScope.start(block: suspend CoroutineScope.() -> Unit): Job? {
        return try {
            showLoading(true)
            this.launch(block = block)
        } catch (e: Exception) {
            null
        }
    }

    fun showLoading(isShow: Boolean) {
        _eventLoading.sendValue(isShow)
    }

    fun CoroutineScope.startWithLoadMore(block: suspend CoroutineScope.() -> Unit): Job {
        if (!isRefreshOrLoadMore) {
            showLoading(true)
        }
        return this.launch(block = block)
    }

    fun CoroutineScope.startWithMultiCall(block: suspend CoroutineScope.() -> Unit): Job {
        if (!isMultiCall) {
            showLoading(true)
        }
        return this.launch(block = block)
    }

    suspend fun <T : Any> makeApiCall(call: suspend () -> Call<T>): T? {
        return try {
            val result = call.invoke().await()
            result.run {
                return this
            }
        } catch (e: Exception) {
            null
        }
    }

    fun <T> UiState<T>.handleResponse(
        observeLoading: Boolean = true,
        success: ((UiState<T>) -> Unit)
    ) {
        try {
            if (this.state == RepoState.SUCCESS) {
                success.invoke(this)
            } else if (this.state == RepoState.FAIL){
                _eventErrorMessage.sendValue(this.message.toString())
                success.invoke(UiState(RepoState.FAIL, null, this.message.toString(), 0))
            }
            if (observeLoading) showLoading(false)
        } catch (e: Exception) {
            success.invoke(UiState(RepoState.FAIL, null, this.message.toString(), 0))
            showLoading(false)
        }

    }

    inline fun <T> Call<T>.callApi(call: ((T?) -> Unit)) {
        showLoading(true)
        val response = try {
            execute()
        } catch (e: TimeoutException) {
            showLoading(false)
            throw TimeoutException()
        } catch (e: Throwable) {
            showLoading(false)
            throw e
        }
        showLoading(false)
        if (response.isSuccessful) {
            call.invoke(response.body())
        } else {
            Timber.tag("Error").e(response.message())
            Timber.tag("ErrorCode").e(response.code().toString())
            _eventErrorMessage.sendValue(response.message())
        }
    }

    fun <T> Call<T>.callApiAny(): Any? {
        val response = try {
            execute()
        } catch (e: TimeoutException) {
            throw TimeoutException()
        } catch (e: Throwable) {
            throw e
        }
        return if (response.isSuccessful) {
            response.body()
        } else {
            Timber.tag("Error").d(response.message())
            Timber.tag("ErrorCode").d(response.code().toString())
            response.message()
        }
    }

    fun clearAll() {
        mLife.stop().destroy()
        viewModelJob.cancel()
        _eventLoading.sendValue(false)
        _eventErrorMessage.sendValue("")
        _eventNoData.sendValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        clearAll()
    }

}
