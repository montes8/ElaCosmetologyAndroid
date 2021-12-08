package com.example.elacosmetologyandroid.usecases.repository

import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.model.VideoModel
import com.example.elacosmetologyandroid.model.ParamModel


interface IAppRepositoryNetwork {

     suspend fun loadListMusic():List<VideoModel>

     suspend fun loadBanner():List<ModelGeneric>

     suspend fun loadParam():ParamModel

     suspend fun saveParam(param : ParamModel):ParamModel

     suspend fun updateParam(param : ParamModel):ParamModel

     suspend fun saveVideo(video : VideoModel):VideoModel
}