package com.example.elacosmetologyandroid.repository.network

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface ServiceApi {

    @POST("api/auth/login")
    suspend fun login(): Response<UserResponse>

    @POST("api/user")
    suspend fun register(@Body userResponse: UserResponse): Response<UserResponse>

    @PUT("api/user")
    suspend fun updateUser(): Response<UserResponse>

    @DELETE("api/user")
    suspend fun deleteUser(): Response<UserResponse>

    @GET("api/user")
    suspend fun listUser(): Response<List<User>>
}