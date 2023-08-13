package com.dev.demoapp.view.viewmodel

import com.dev.demoapp.dev.xbase.BaseMvvmViewModel
import com.dev.demoapp.dev.xbase.sendValue
import com.dev.demoapp.model.Categories
import com.dev.demoapp.model.RepoState
import com.dev.demoapp.model.UiState
import com.dev.demoapp.model.UserResponse
import com.dev.demoapp.network.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel constructor(
    private val response: MainRepository
) : BaseMvvmViewModel() {

    private var _eventData = MutableStateFlow(UiState<List<Categories>>())

    var eventData: StateFlow<UiState<List<Categories>>> = _eventData
    fun getCategories(map: HashMap<String, Any>) {
        ioScope.start {
            response.getCategories(map).handleResponse {
                _eventData.sendValue(
                    it
                )
            }
        }
    }

}
