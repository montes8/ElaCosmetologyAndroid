package com.example.elacosmetologyandroid.usecases


import org.koin.dsl.module

val useCaseModule = module {
    single { AuthUseCase() }
}