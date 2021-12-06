package com.example.elacosmetologyandroid.repository.network

import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.entity.DataUserResponse
import com.example.elacosmetologyandroid.repository.network.entity.ImageResponse
import com.example.elacosmetologyandroid.repository.network.entity.MusicGenericResponse
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import com.example.elacosmetologyandroid.repository.network.entity.response.ParamResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ServiceApi {


    @Multipart
    @POST("api/uploads")
    suspend fun saveProfile(
        @Part archivo : MultipartBody.Part): Response<ImageResponse>

    @Multipart
    @PUT("api/uploads/{coleccion}/{id}")
    suspend fun imageProfile(
        @Path("coleccion")coleccion : String,
        @Path("id")id : String,
        @Part archivo : MultipartBody.Part): Response<UserResponse>

    @Multipart
    @PUT("api/uploads/banner/{coleccion}/{id}")
    suspend fun imageBanner(
        @Path("coleccion")coleccion : String,
        @Path("id")id : String,
        @Part archivo : MultipartBody.Part): Response<UserResponse>


    @GET("api/uploads/{coleccion}/{id}")
    @Streaming
    suspend fun loadImage(@Path("coleccion")coleccion : String,
                          @Path("id")id : String): Response<ResponseBody>


    @POST("api/auth/login")
    suspend fun login(@Body userResponse: UserResponse): Response<DataUserResponse>

    @POST("api/user")
    suspend fun register(@Body userResponse: UserResponse): Response<DataUserResponse>

    @PUT("api/user/{id}")
    suspend fun updateUser(@Path("id")id : String,
        @Body userResponse: UserResponse): Response<UserResponse>

    @DELETE("api/user/{id}")
    suspend fun inactiveUser(@Path("id")id : String): Response<UserResponse>

    @DELETE("api/user/delete/{id}")
    suspend fun deleteUser(@Path("id")id : String): Response<UserResponse>

    @GET("api/user")
    suspend fun listUser(): Response<List<User>>

    @GET("api/config/parameter")
    suspend fun listMusic(): Response<List<MusicGenericResponse>>

    @GET("api/config/banner")
    suspend fun loadBanner(): Response<List<ModelGeneric>>


    //servicios de configuracion por defaul

    @GET("api/config/param")
    suspend fun loadParam(): Response<List<ParamResponse>>

    @POST("api/config/param")
    suspend fun saveParam(@Body paramResponse: ParamResponse): Response<ParamResponse>
}