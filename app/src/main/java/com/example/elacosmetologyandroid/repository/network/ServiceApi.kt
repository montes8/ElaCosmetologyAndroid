package com.example.elacosmetologyandroid.repository.network

import com.example.elacosmetologyandroid.model.BannerModel
import com.example.elacosmetologyandroid.model.ProductModel
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.entity.ImageResponse
import com.example.elacosmetologyandroid.repository.network.entity.response.*
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
        @Body userResponse: UserResponse
    ): Response<UserResponse>

    @DELETE("api/user/{id}")
    suspend fun inactiveUser(@Path("id")id : String): Response<UserResponse>

    @DELETE("api/user/delete/{id}")
    suspend fun deleteUser(@Path("id")id : String): Response<UserResponse>

    @GET("api/user")
    suspend fun listUser(): Response<List<User>>

    @GET("api/config/parameter")
    suspend fun listMusic(): Response<List<VideoResponse>>

    @GET("api/config/banner")
    suspend fun loadBanner(): Response<List<BannerResponse>>


    //servicios de configuracion por defaul

    @GET("api/config/param")
    suspend fun loadParam(): Response<ParamResponse>

    @POST("api/config/param")
    suspend fun saveParam(@Body paramResponse: ParamResponse): Response<ParamResponse>

    @PUT("api/config/param/{id}")
    suspend fun updateParam(@Path("id")id : String,
                            @Body paramResponse: ParamResponse): Response<ParamResponse>


    @GET("api/config/video")
    suspend fun loadVideo(): Response<List<VideoResponse>>

    @POST("api/config/video")
    suspend fun saveVideo(@Body videoResponse: VideoResponse): Response<VideoResponse>

    @PUT("api/config/video/{id}")
    suspend fun updateVideo(@Path("id")id : String,
                            @Body paramResponse: VideoResponse
    ): Response<VideoResponse>


    @POST("api/config/banner")
    suspend fun saveBanner(@Body bannerModel: BannerResponse): Response<BannerResponse>

    //todo services category

    @GET("api/category")
    suspend fun loadCategory(): Response<List<CategoryResponse>>


    //todo services product


    @GET("api/product")
    suspend fun loadProduct(): Response<List<ProductResponse>>
}