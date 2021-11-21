package com.example.elacosmetologyandroid.usecases

import com.example.elacosmetologyandroid.model.User
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthUseCase : KoinComponent {

    private val iAuthRepositoryNetwork: IAuthRepositoryNetwork by inject()


    suspend fun login(email : String,pass: String) = iAuthRepositoryNetwork.login(email,pass)
    suspend fun register(register : User) = iAuthRepositoryNetwork.register(register)

}