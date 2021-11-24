package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import org.koin.core.KoinComponent
import org.koin.core.inject

class AuthUseCase : KoinComponent {

    private val iAuthRepositoryNetwork: IAuthRepositoryNetwork by inject()
    private val appRepositoryPreference: AppRepositoryPreference by inject()

     suspend fun login(email : String,pass: String):User{
         val response = iAuthRepositoryNetwork.login(email,pass)
         appRepositoryPreference.saveToken(response.second)
         return response.first
     }

     suspend fun register(register : User):User {
         val responseRegister = iAuthRepositoryNetwork.register(register)
         val response = iAuthRepositoryNetwork.login(responseRegister.email,register.password)
         appRepositoryPreference.saveToken(response.second)
         return response.first
     }

     fun session() = appRepositoryPreference.getToken().isNotEmpty()

}