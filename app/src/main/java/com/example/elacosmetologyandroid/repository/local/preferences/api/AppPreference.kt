package com.example.elacosmetologyandroid.repository.local.preferences.api


import com.example.elacosmetologyandroid.repository.local.preferences.manager.PreferencesManager
import com.example.elacosmetologyandroid.repository.local.preferences.utils.PREFERENCE_LIST_MUSIC
import com.example.elacosmetologyandroid.repository.local.preferences.utils.PREFERENCE_TOKEN
import com.example.elacosmetologyandroid.repository.local.preferences.utils.PREFERENCE_ROL_USER
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import org.koin.core.KoinComponent
import org.koin.core.inject


class AppPreference :
    AppRepositoryPreference, KoinComponent {

    private val sharedPreferenceManager: PreferencesManager by inject()

    override fun getToken(): String {
        return sharedPreferenceManager.getString(PREFERENCE_TOKEN)
    }

    override fun saveToken(value: String) {
        sharedPreferenceManager.setValue(PREFERENCE_TOKEN, value)
    }

    override fun getUser(): String {
        return sharedPreferenceManager.getString(PREFERENCE_ROL_USER)
    }

    override fun saveUser(value: String) {
        sharedPreferenceManager.setValue(PREFERENCE_ROL_USER, value)
    }

    override fun getListMusic(): String {
        return sharedPreferenceManager.getString(PREFERENCE_LIST_MUSIC)
    }

    override fun saveListMusic(value: String) {
        sharedPreferenceManager.setValue(PREFERENCE_LIST_MUSIC, value)
    }
}