package com.dev.demoapp.network

import com.dev.demoapp.di.provideHttpClient
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigDecimal


abstract class BaseApiClient<Client> {

    private var apiClient: Client? = null   // provider interface api

    private fun getRetrofit(url: String): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(url)
            addCallAdapterFactory(CoroutineCallAdapterFactory())
            addConverterFactory(provideGsonConvertFactory())
            addConverterFactory(MoshiConverterFactory.create(moshi))
            client(provideHttpClient())
        }.build()
    }

    fun getClient() : Client{
        if (apiClient == null) {
            apiClient = getRetrofit(getBaseUrl()).create(getClassM())
        }
        return apiClient!!
    }

    private val moshi = Moshi.Builder()
        .add(BigDecimalAdapter)
        .build()

    object BigDecimalAdapter {
        @FromJson
        fun fromJson(string: String) = BigDecimal(string)

        @ToJson
        fun toJson(value: BigDecimal) = value.toString()
    }

    abstract fun getClassM(): Class<Client>

    private fun provideGsonConvertFactory() = GsonConverterFactory
        .create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())

    abstract fun getBaseUrl() : String

    abstract fun authenticationInterceptor(): Interceptor

}