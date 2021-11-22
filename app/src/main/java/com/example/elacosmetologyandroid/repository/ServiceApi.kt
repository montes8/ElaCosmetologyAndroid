package com.example.elacosmetologyandroid.repository

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface ServiceApi {

    @POST("validate")
    fun login(): Response<UserResponse>

    @POST("validate")
    fun register(): Response<UserResponse>

    @GET("validate")
    fun listUser(): Response<List<User>>
}