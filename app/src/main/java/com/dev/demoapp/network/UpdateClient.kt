package com.dev.demoapp.network

import com.dev.demoapp.dev.utils.ContainsUtils
import okhttp3.Interceptor

object UpdateClient : BaseApiClient<MainApi>() {

    override fun getBaseUrl() = ContainsUtils.HOST

    override fun authenticationInterceptor(): Interceptor {
        return authInterceptor
    }

    private val authInterceptor = Interceptor { chain ->
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("X-API-KEY","3EB76D87D97C427943957C555AB0B60847582D38CB1688ED86C59251206305E3")
            .addHeader("Content-Type", "application/json")
            .build()
        chain.proceed(newRequest)
    }

    override fun getClassM(): Class<MainApi> = MainApi::class.java

}
