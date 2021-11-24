package com.example.elacosmetologyandroid.manager

import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject

object UserTemporary : KoinComponent {
    private val authUseCase: AuthUseCase by inject()

    var duration = 0

    fun getUser() = authUseCase.fetchUser()
}