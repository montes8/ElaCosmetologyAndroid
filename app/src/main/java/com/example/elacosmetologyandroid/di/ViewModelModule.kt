package com.example.elacosmetologyandroid.di

import com.example.elacosmetologyandroid.ui.AppViewModel
import com.example.elacosmetologyandroid.ui.address.AddressMapViewModel
import com.example.elacosmetologyandroid.ui.admin.ParametersViewModel
import com.example.elacosmetologyandroid.ui.login.LoginViewModel
import com.example.elacosmetologyandroid.ui.profile.ProfileViewModel
import com.example.elacosmetologyandroid.ui.register.RegisterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { AppViewModel() }
    viewModel { LoginViewModel() }
    viewModel { RegisterViewModel() }
    viewModel { AddressMapViewModel()}
    viewModel { ProfileViewModel() }
    viewModel { ParametersViewModel() }
}
