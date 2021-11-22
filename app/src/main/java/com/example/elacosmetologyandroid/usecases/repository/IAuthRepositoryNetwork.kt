package com.example.elacosmetologyandroid.usecases.repository

import com.example.elacosmetologyandroid.model.User


interface IAuthRepositoryNetwork {
     suspend fun login(email : String, password : String): User
     suspend fun register(register : User):User
}