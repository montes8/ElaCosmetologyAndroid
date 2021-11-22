package com.example.elacosmetologyandroid.application

import android.app.Application
import com.example.elacosmetologyandroid.BuildConfig
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.di.viewModelModule
import com.example.elacosmetologyandroid.repository.NAME_BASE_URL
import com.example.elacosmetologyandroid.repository.local.preferences.di.preferencesModule
import com.example.elacosmetologyandroid.repository.local.preferences.utils.ENCRYPTION_KEY
import com.example.elacosmetologyandroid.repository.network.di.moduleNetwork
import com.example.elacosmetologyandroid.usecases.di.useCaseModule
import org.koin.android.ext.android.getKoin
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
                    viewModelModule,
                    moduleNetwork,
                    preferencesModule,
                    useCaseModule
                )
            )

            getKoin().setProperty(NAME_BASE_URL,
                BuildConfig.BASE_URL
            )
            getKoin().setProperty(ENCRYPTION_KEY, getString(R.string.encryption_key))
        }

    }

}