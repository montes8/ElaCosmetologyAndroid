package com.example.elacosmetologyandroid.repository

import com.example.elacosmetologyandroid.repository.network.api.AuthNetwork
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import org.koin.dsl.module


val networkModule = module {
    single<IAuthRepositoryNetwork> { AuthNetwork() }
}