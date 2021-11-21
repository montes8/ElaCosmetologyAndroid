package com.example.elacosmetologyandroid.di

import com.example.elacosmetologyandroid.ui.login.LoginViewModel
import com.example.elacosmetologyandroid.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
}
