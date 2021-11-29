package com.example.elacosmetologyandroid.repository.network.api

import android.content.Context
import com.example.elacosmetologyandroid.model.MusicGeneric
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.exception.BaseNetwork
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.entity.MusicGenericResponse
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import com.example.elacosmetologyandroid.repository.network.exception.toCompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.utils.validateBody
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import com.example.elacosmetologyandroid.utils.CONFIG_MUSIC
import com.example.elacosmetologyandroid.utils.getData
import org.koin.core.inject


class AuthNetwork : IAuthRepositoryNetwork, BaseNetwork(){

    private val serviceApi: ServiceApi by inject()
    private val context: Context by inject()

    override suspend fun login(email: String, password: String): Pair<User,String> {
        return executeWithConnection {
            val response = serviceApi.login(UserResponse(email,password))
            var data :Pair<User,String>? = null
            if (response.isSuccessful) {
                data = response.validateBody().toLogin()
            }
            data?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }


    override  suspend fun register(register: User): User {
        return executeWithConnection {
            val response = serviceApi.register(UserResponse.toUserResponse(register))
            var user : User? = null
            if (response.isSuccessful && response.body() != null) {
                user = response.validateBody().toUser()
            }
            user?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }

    override suspend fun loadListMusic(): List<MusicGeneric> {
        return executeWithConnection {
            val response = serviceApi.listMusic()
            var listMusic : List<MusicGeneric>? = null
            if (response.isSuccessful && response.body() != null) {
                listMusic = MusicGenericResponse.toListMusic(response.validateBody())
            }
            listMusic?: getData(context, CONFIG_MUSIC)
        }
    }
}