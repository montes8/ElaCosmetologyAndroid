package com.example.elacosmetologyandroid.repository

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ServiceApi {

    @POST("api/user")
    fun login(): Response<UserResponse>

    @POST("api/user")
    fun register(): Response<UserResponse>

    @PUT("api/user")
    fun updateUser(): Response<UserResponse>

    @DELETE("api/user")
    fun deleteUser(): Response<UserResponse>

    @GET("api/user")
    fun listUser(): Response<List<User>>
}