package com.example.elacosmetologyandroid.manager

import android.content.Context
import com.example.elacosmetologyandroid.model.MusicGeneric
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AppUseCase
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.CONFIG_MUSIC
import com.example.elacosmetologyandroid.utils.getData
import org.koin.core.KoinComponent
import org.koin.core.inject

object UserTemporary : KoinComponent, BaseViewModel() {
    private val authUseCase: AuthUseCase by inject()
    private val context: Context by inject()

    var musicGeneric = MusicGeneric()

    var listMusic :  List<MusicGeneric> = getData(context, CONFIG_MUSIC)

    fun getUser() = authUseCase.fetchUser()

}