package com.dev.demoapp.di

import com.dev.demoapp.network.repository.MainRepository
import com.dev.demoapp.network.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {

  single { UserRepository(get()) }

  single { MainRepository(get()) }

}
