package com.dev.demoapp.di

import com.dev.demoapp.BuildConfig
import com.dev.demoapp.dev.utils.ContainsUtils
import com.dev.demoapp.network.MainApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

val networkModule = module {

    single {
        Retrofit.Builder()
            .client(provideHttpClient())
            .baseUrl(ContainsUtils.HOST)
            .addConverterFactory(provideGsonConvertFactory())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    single { get<Retrofit>().create(MainApi::class.java) }
}

val authInterceptor = Interceptor { chain ->
    val request = chain.request()
    val newRequest = request.newBuilder()
        .addHeader("Content-Type", "application/json")
        .build()
    chain.proceed(newRequest)
}

private fun provideGsonConvertFactory() = GsonConverterFactory
    .create(GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create())


fun provideHttpClient(): OkHttpClient {
    return try {
        val trustAllCerts: Array<TrustManager> = arrayOf(
            object : X509TrustManager {
                override fun checkClientTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun checkServerTrusted(
                    chain: Array<X509Certificate?>?,
                    authType: String?
                ) {
                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            }
        )

        // Install the all-trusting trust manager
        val sslContext: SSLContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory
        var builder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(ContainsUtils.TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(ContainsUtils.TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(ContainsUtils.TIME_OUT, TimeUnit.SECONDS)
        if (BuildConfig.isEnableLog) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder = builder
                .addInterceptor(interceptor)
        }
//        if (ContainsUtils.HOST.contains("https")) {
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _: String?, _: SSLSession? -> true }
//        }
        builder = builder.addInterceptor(authInterceptor)
        builder.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}
