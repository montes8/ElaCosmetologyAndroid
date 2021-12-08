package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.model.VideoModel
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import com.example.elacosmetologyandroid.usecases.repository.IAppRepositoryNetwork
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppUseCase : KoinComponent {

    private val iAuthRepositoryNetwork: IAppRepositoryNetwork by inject()

    private val appRepositoryPreference: AppRepositoryPreference by inject()

    suspend fun session() : Boolean{
        val parameter = iAuthRepositoryNetwork.loadListMusic()
        UserTemporary.listMusic = parameter
        return appRepositoryPreference.getToken().isNotEmpty()
    }

    fun logout() = appRepositoryPreference.saveToken(EMPTY)

    suspend fun loadBanner() = iAuthRepositoryNetwork.loadBanner()

    suspend fun loadParam() = iAuthRepositoryNetwork.loadParam()

    suspend fun saveParam(param : ParamModel) = iAuthRepositoryNetwork.saveParam(param)

    suspend fun updateParam(param : ParamModel) = iAuthRepositoryNetwork.updateParam(param)

    suspend fun saveVideo(video : VideoModel) = iAuthRepositoryNetwork.saveVideo(video)

}