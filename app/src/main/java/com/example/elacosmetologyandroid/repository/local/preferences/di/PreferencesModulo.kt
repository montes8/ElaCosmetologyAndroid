package com.example.elacosmetologyandroid.repository.local.preferences.di

import com.example.elacosmetologyandroid.repository.ENCRYPTION_KEY
import com.example.elacosmetologyandroid.repository.local.preferences.api.AppPreference
import com.example.elacosmetologyandroid.repository.local.preferences.manager.PreferencesManager
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import org.koin.dsl.module

val preferencesModule = module {
    single {
        PreferencesManager(
            get(),
            getProperty(ENCRYPTION_KEY)
        )
    }
    single<AppRepositoryPreference> {
        AppPreference(
            get()
        )
    }
}