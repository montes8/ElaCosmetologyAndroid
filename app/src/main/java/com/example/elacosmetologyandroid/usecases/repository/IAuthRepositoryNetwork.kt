package com.example.elacosmetologyandroid.usecases.repository

import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.model.MusicGeneric
import com.example.elacosmetologyandroid.model.User


interface IAuthRepositoryNetwork {

     suspend fun login(email : String, password : String): Pair<User, String>

     suspend fun register(register : User):User

     suspend fun loadListMusic():List<MusicGeneric>

     suspend fun loadBanner():List<ModelGeneric>
}