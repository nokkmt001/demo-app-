
package com.dev.demoapp.di

import com.dev.demoapp.view.viewmodel.LoginViewModel
import com.dev.demoapp.view.viewmodel.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

  viewModel { MainViewModel(get()) }

  viewModel { LoginViewModel(get()) }

  viewModel { ScanViewModel(get()) }

}
