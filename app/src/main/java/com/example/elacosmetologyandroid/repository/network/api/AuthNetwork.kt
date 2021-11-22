package com.example.elacosmetologyandroid.repository.network.api

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.exception.BaseNetwork
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import com.example.elacosmetologyandroid.repository.network.exception.toCompleteErrorModel
import com.example.elacosmetologyandroid.repository.validateBody
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import org.koin.core.inject


class AuthNetwork : IAuthRepositoryNetwork, BaseNetwork(){

    private val serviceApi: ServiceApi by inject()

    override suspend fun login(email: String, password: String): User {
        return executeWithConnection {
            val response = serviceApi.login()
            val user = User()
            if (response.isSuccessful && response.body() != null) {
                response.validateBody().toUser()
            } else throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
            user
        }
    }


    override  suspend fun register(register: User): User {
        return executeWithConnection {
            val response = serviceApi.register(UserResponse.toUserResponse(register))
            val user = User()
            if (response.isSuccessful && response.body() != null) {
                response.validateBody().toUser()
            } else throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
            user
        }
    }


}