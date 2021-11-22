package com.example.elacosmetologyandroid.repository.network

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface ServiceApi {

    @POST("api/user")
    fun login(): Response<UserResponse>

    @POST("api/user")
    fun register(@Body userResponse: UserResponse): Response<UserResponse>

    @PUT("api/user")
    fun updateUser(): Response<UserResponse>

    @DELETE("api/user")
    fun deleteUser(): Response<UserResponse>

    @GET("api/user")
    fun listUser(): Response<List<User>>
}