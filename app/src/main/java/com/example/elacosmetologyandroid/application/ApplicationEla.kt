package com.example.elacosmetologyandroid.application

import android.app.Application
import com.example.elacosmetologyandroid.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationEla : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@ApplicationEla)
            modules(
                listOf(
                    viewModelModule
                )
            )
        }



    }



}