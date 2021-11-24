package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference

import org.koin.core.KoinComponent
import org.koin.core.inject

class AppUseCase : KoinComponent {

    private val appRepositoryPreference: AppRepositoryPreference by inject()

     fun session() = appRepositoryPreference.getToken().isNotEmpty()

}