package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.model.BannerModel
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.model.VideoModel
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import com.example.elacosmetologyandroid.usecases.repository.IAppRepositoryNetwork
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import com.example.elacosmetologyandroid.utils.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class AppUseCase : KoinComponent {

    private val iAppRepositoryNetwork: IAppRepositoryNetwork by inject()

    private val iAuthRepositoryNetwork: IAuthRepositoryNetwork by inject()

    private val appRepositoryPreference: AppRepositoryPreference by inject()

    suspend fun session() : Boolean{
        val movies = iAppRepositoryNetwork.loadListMusic()
        val param = iAppRepositoryNetwork.loadParam()
        UserTemporary.listMusic = movies
        UserTemporary.paramDefault = if (param.title.isNotEmpty())param else ParamModel(EMPTY,
            TITLE_DEFAULT, DESCRIPTION_DEFAULT,true)
        return appRepositoryPreference.getToken().isNotEmpty()
    }

    fun logout() = appRepositoryPreference.saveToken(EMPTY)

    suspend fun loadBanner() = iAppRepositoryNetwork.loadBanner()

    suspend fun loadParam() = iAppRepositoryNetwork.loadParam()

    suspend fun saveParam(param : ParamModel) = iAppRepositoryNetwork.saveParam(param)

    suspend fun updateParam(param : ParamModel) = iAppRepositoryNetwork.updateParam(param)

    suspend fun saveVideo(video : VideoModel) = iAppRepositoryNetwork.saveVideo(video)

    suspend fun saveBanner(banner : BannerModel,file : File):Boolean {
     val response = iAppRepositoryNetwork.saveBanner(banner)
     return iAuthRepositoryNetwork.updateImage(TYPE_BANNER, response.id,file)
    }


}