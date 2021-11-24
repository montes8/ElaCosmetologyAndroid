package com.example.elacosmetologyandroid.manager

import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

object UserTemporary : KoinComponent, BaseViewModel() {
    private val authUseCase: AuthUseCase by inject()

    var duration = 0
    var durationTotal = 0

    fun getUser() = authUseCase.fetchUser()
}