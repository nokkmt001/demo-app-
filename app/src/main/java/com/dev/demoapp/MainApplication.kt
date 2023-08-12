package com.dev.demoapp

import android.app.Application
import android.widget.Toast
import com.dev.demoapp.dev.common.AppResource
import com.dev.demoapp.dev.debug.DebugTree

import com.dev.demoapp.di.networkModule
import com.dev.demoapp.di.repositoryModule
import com.dev.demoapp.di.viewModelModule
import com.orhanobut.hawk.Hawk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class MainApplication : Application() {

    private var mSelf: MainApplication? = null

    override fun onCreate() {
        super.onCreate()
        mSelf = this
        try {
            AppResource.init(this)
            Hawk.init(this).build()

            startKoin {
                androidContext(this@MainApplication)
                modules(networkModule)
                modules(repositoryModule)
                modules(viewModelModule)
            }

            if (BuildConfig.isEnableLog) {
                Timber.plant(DebugTree())
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
