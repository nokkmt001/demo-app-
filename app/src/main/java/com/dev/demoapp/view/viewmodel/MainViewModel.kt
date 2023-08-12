package com.dev.demoapp.view.viewmodel

import com.dev.demoapp.dev.xbase.BaseMvvmViewModel
import com.dev.demoapp.network.repository.MainRepository

class MainViewModel constructor(
    private val response: MainRepository
) : BaseMvvmViewModel() {

}
