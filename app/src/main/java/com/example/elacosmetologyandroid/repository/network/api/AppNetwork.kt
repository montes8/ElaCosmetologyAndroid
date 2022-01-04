package com.example.elacosmetologyandroid.repository.network.api

import android.content.Context
import com.example.elacosmetologyandroid.model.BannerModel
import com.example.elacosmetologyandroid.model.VideoModel
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.entity.response.BannerResponse
import com.example.elacosmetologyandroid.repository.network.entity.response.VideoResponse
import com.example.elacosmetologyandroid.repository.network.entity.response.ParamResponse
import com.example.elacosmetologyandroid.repository.network.exception.BaseNetwork
import com.example.elacosmetologyandroid.repository.network.exception.toCompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.mapper.getResultOrThrowException
import com.example.elacosmetologyandroid.repository.network.utils.validateBody
import com.example.elacosmetologyandroid.usecases.repository.IAppRepositoryNetwork
import com.example.elacosmetologyandroid.utils.CONFIG_MUSIC
import com.example.elacosmetologyandroid.utils.getData
import org.koin.core.inject

class AppNetwork : IAppRepositoryNetwork, BaseNetwork(){

    private val serviceApi: ServiceApi by inject()
    private val context: Context by inject()

    override suspend fun loadListMusic(): List<VideoModel> {
        val response = serviceApi.loadVideo()
            var listMusic : List<VideoModel>? = null
            if (response.isSuccessful && response.body() != null) {
                listMusic = VideoResponse.toListVideo(response.validateBody())
            }
        return  if (listMusic?.isNotEmpty()?:getData(context, CONFIG_MUSIC))listMusic?:getData(context, CONFIG_MUSIC) else getData(context, CONFIG_MUSIC)

    }

    override suspend fun loadBanner(): List<BannerModel> {
            val response = serviceApi.loadBanner()
            val result = response.getResultOrThrowException()
             return BannerResponse.toListBannerModel(result)
    }

    override suspend fun loadParam(): ParamModel {
        return executeWithConnection {
            val response = serviceApi.loadParam()
            var list : ParamModel? = null
            if (response.isSuccessful && response.body() != null) {
                list = ParamResponse.toParamModel(response.validateBody())
            }
            list?: ParamModel()
        }
    }

    override suspend fun saveParam(param: ParamModel): ParamModel {
        return executeWithConnection {
            val response = serviceApi.saveParam(ParamResponse.toParamResponse(param))
            var paramModel : ParamModel? = null
            if (response.isSuccessful && response.body() != null) {
                paramModel = ParamResponse.toParamModel(response.validateBody())
            }
            paramModel?: throw response.errorBody()?.toCompleteErrorModel(response.code())?.getException() ?: Exception()
        }
    }

    override suspend fun updateParam(param: ParamModel): ParamModel {
        return executeWithConnection {
            val response = serviceApi.updateParam(param.id,ParamResponse.toParamResponse(param))
            var paramModel : ParamModel? = null
            if (response.isSuccessful && response.body() != null) {
                paramModel = ParamResponse.toParamModel(response.validateBody())
            }
            paramModel?: throw response.errorBody()?.toCompleteErrorModel(response.code())?.getException() ?: Exception()
        }
    }

    override suspend fun saveVideo(video: VideoModel): VideoModel {
        return executeWithConnection {
            val response = serviceApi.saveVideo(VideoResponse.toVideoResponse(video))
            var videoModel : VideoModel? = null
            if (response.isSuccessful && response.body() != null) {
                videoModel = VideoResponse.toVideo(response.validateBody())
            }
            videoModel?: throw response.errorBody()?.toCompleteErrorModel(response.code())?.getException() ?: Exception()
        }
    }

    override suspend fun saveBanner(banner: BannerModel): BannerModel {
        return executeWithConnection {
            val response = serviceApi.saveBanner(BannerResponse.toBannerResponse(banner))
            var bannerModel : BannerModel? = null
            if (response.isSuccessful && response.body() != null) {
                bannerModel = BannerResponse.toBannerModel(response.validateBody())
            }
            bannerModel?: throw response.errorBody()?.toCompleteErrorModel(response.code())?.getException() ?: Exception()
        }
    }
}