package com.example.elacosmetologyandroid.repository.network.api

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.exception.BaseNetwork
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import com.example.elacosmetologyandroid.repository.network.exception.toCompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.utils.validateBody
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import org.koin.core.inject


class AuthNetwork : IAuthRepositoryNetwork, BaseNetwork(){

    private val serviceApi: ServiceApi by inject()

    override suspend fun login(email: String, password: String): Pair<User,String> {

        return executeWithConnection {
            val response = serviceApi.login(UserResponse(email,password))
            val data :Pair<User,String>?
            if (response.isSuccessful) {
                data = response.validateBody().toLogin()
            } else throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
            data
        }
    }


    override  suspend fun register(register: User): User {
        return executeWithConnection {
            val response = serviceApi.register(UserResponse.toUserResponse(register))
            val user : User?
            if (response.isSuccessful && response.body() != null) {
                user = response.validateBody().toUser()
            } else throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
            user
        }
    }

}