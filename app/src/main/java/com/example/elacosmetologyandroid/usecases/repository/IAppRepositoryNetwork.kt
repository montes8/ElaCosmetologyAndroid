package com.example.elacosmetologyandroid.usecases.repository

import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.model.MusicGeneric


interface IAppRepositoryNetwork {

     suspend fun loadListMusic():List<MusicGeneric>

     suspend fun loadBanner():List<ModelGeneric>
}