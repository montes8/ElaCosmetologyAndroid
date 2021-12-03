package com.example.elacosmetologyandroid.repository.network.api

import android.content.Context
import android.graphics.Bitmap
import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.model.MusicGeneric
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.exception.BaseNetwork
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.entity.MusicGenericResponse
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import com.example.elacosmetologyandroid.repository.network.exception.toCompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.utils.validateBody
import com.example.elacosmetologyandroid.usecases.repository.IAppRepositoryNetwork
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import com.example.elacosmetologyandroid.utils.CONFIG_MUSIC
import com.example.elacosmetologyandroid.utils.getData
import org.koin.core.inject


class AppNetwork : IAppRepositoryNetwork, BaseNetwork(){

    private val serviceApi: ServiceApi by inject()
    private val context: Context by inject()

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

    override suspend fun loadBanner(): List<ModelGeneric> {
        return executeWithConnection {
            val response = serviceApi.loadBanner()
            var list : List<ModelGeneric>? = null
            if (response.isSuccessful && response.body() != null) {
                list = ModelGeneric.toListModelGeneric(response.validateBody())
            }
            list?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }
}