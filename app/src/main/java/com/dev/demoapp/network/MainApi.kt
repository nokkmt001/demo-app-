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

    /**
     * qr code
     */

    @POST(ContainsUtils.updateQr)
    fun updateQrAsync(@HeaderMap header: HashMap<String, Any>, @Body item: QrUpdate): Deferred<Response<ApiResponse<QrData>>>

    @POST(ContainsUtils.getQr)
    fun getQrAsync(@HeaderMap header: HashMap<String, Any>, @Body item: QrScanRequest): Deferred<Response<ApiResponse<QrData>>>

    /**
     * history
     */

    @POST(ContainsUtils.getHistory)
    fun getHistoryAsync(@HeaderMap header: HashMap<String, Any>, @Body item: HistoryParams): Deferred<Response<HistoryDataResponse>>

    @POST(ContainsUtils.deleteHistory)
    fun deleteHistoryAsync(@HeaderMap header: HashMap<String, Any>, @Body item: HistoryParams): Deferred<Response<ApiResponse<List<HistoryDataRemove>>>>

    /**
     * vehicle
     */
    @GET(ContainsUtils.getVehicleType)
    fun getVehicleAsync(@HeaderMap header: HashMap<String, Any>): Deferred<Response<ApiResponse<List<VehicleType>>>>

    /**
     * time
     */
    @GET(ContainsUtils.getTimeType)
    fun getTimeAsync(@HeaderMap header: HashMap<String, Any>, @Query ("s") store: String): Deferred<Response<List<TimeShip>>>

    @GET(ContainsUtils.checkUpdate)
    fun getUpdateAsync(): Deferred<Response<ChangeLog>>

}
