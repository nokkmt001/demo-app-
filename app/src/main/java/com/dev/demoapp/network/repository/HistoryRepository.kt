package com.dev.demoapp.network.repository

import com.dev.demoapp.dev.xbase.BaseRepository
import com.dev.demoapp.model.*
import com.dev.demoapp.network.MainApi

class HistoryRepository constructor(
    private val mainApi: MainApi
) : BaseRepository() {

    suspend fun getHistory(map: HashMap<String, Any>, item: HistoryParams): UiState<HistoryDataResponse> = makeApiCall {
        mainApi.getHistoryAsync(map, item).await()
    }

    suspend fun deleteHistory(map: HashMap<String, Any>, item: HistoryParams): UiState<ApiResponse<List<HistoryDataRemove>>> = makeApiCall {
        mainApi.deleteHistoryAsync(map, item).await()
    }

    suspend fun getQr(map: HashMap<String, Any> ,item: QrScanRequest): UiState<ApiResponse<QrData>> = makeApiCall {
        mainApi.getQrAsync(map, item).await()
    }

}