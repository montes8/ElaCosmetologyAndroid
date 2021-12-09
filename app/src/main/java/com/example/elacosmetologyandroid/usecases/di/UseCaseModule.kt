package com.example.elacosmetologyandroid.usecases.di


import com.example.elacosmetologyandroid.usecases.usecases.AppUseCase
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.usecases.usecases.ProductUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { AuthUseCase() }
    single { AppUseCase() }
    single { ProductUseCase() }
}