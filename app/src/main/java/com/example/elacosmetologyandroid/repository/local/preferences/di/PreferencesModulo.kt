package com.example.elacosmetologyandroid.repository.local.preferences.di

import com.example.elacosmetologyandroid.repository.local.preferences.api.AppPreference
import com.example.elacosmetologyandroid.repository.local.preferences.manager.PreferencesManager
import com.example.elacosmetologyandroid.repository.local.preferences.utils.ENCRYPTION_KEY
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val preferencesModule = module {
    single { PreferencesManager(androidContext(),ENCRYPTION_KEY) }
    single<AppRepositoryPreference> { AppPreference() }
}