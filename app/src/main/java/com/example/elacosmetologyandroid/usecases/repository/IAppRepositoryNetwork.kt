package com.example.elacosmetologyandroid.usecases.repository

import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.model.MusicGeneric
import com.example.elacosmetologyandroid.model.ParamModel


interface IAppRepositoryNetwork {

     suspend fun loadListMusic():List<MusicGeneric>

     suspend fun loadBanner():List<ModelGeneric>

     suspend fun loadParam():List<ParamModel>

     suspend fun saveParam(param : ParamModel):ParamModel

     suspend fun updateParam(param : ParamModel):ParamModel
}