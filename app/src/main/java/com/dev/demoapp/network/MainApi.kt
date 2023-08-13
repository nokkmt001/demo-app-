package com.dev.demoapp.network

import com.dev.demoapp.dev.utils.ContainsUtils
import com.dev.demoapp.model.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface MainApi {

    /**
     * login
     */

    @POST(ContainsUtils.checkLogin)
    fun loginAsync(@Body item: UserLoginPass): Deferred<Response<UserResponse>>

    @POST(ContainsUtils.checkSignup)
    fun signupAsync(@Body item: UserSignupRequest): Deferred<Response<UserSignup>>

    @GET(ContainsUtils.getCategories)
    fun getCategoriesAsync(@HeaderMap header: HashMap<String, Any>): Deferred<Response<List<Categories>>>

}
