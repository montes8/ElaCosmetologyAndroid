package com.example.elacosmetologyandroid.usecases.di


import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { AuthUseCase() }
}