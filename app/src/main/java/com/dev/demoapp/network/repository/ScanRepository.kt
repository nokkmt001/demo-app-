package com.dev.demoapp.network.repository

import com.dev.demoapp.dev.xbase.BaseRepository
import com.dev.demoapp.model.*
import com.dev.demoapp.network.MainApi

class ScanRepository constructor(
    private val mainApi: MainApi
) : BaseRepository() {

    suspend fun getQr(map: HashMap<String, Any> ,item: QrScanRequest): UiState<ApiResponse<QrData>> = makeApiCall {
        mainApi.getQrAsync(map, item).await()
    }

    suspend fun updateQr(map: HashMap<String, Any> ,item: QrUpdate): UiState<ApiResponse<QrData>> = makeApiCall {
        mainApi.updateQrAsync(map, item).await()
    }

    suspend fun getVehicleType(map: HashMap<String, Any> ,): UiState<ApiResponse<List<VehicleType>>> = makeApiCall {
        mainApi.getVehicleAsync(map).await()
    }

    suspend fun getTimeType(map: HashMap<String, Any> ,store : String): UiState<List<TimeShip>> = makeApiCall {
        mainApi.getTimeAsync(map, store).await()
    }

}
