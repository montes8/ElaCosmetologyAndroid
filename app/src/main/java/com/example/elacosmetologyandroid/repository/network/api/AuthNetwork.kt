package com.example.elacosmetologyandroid.repository.network.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.entity.UserResponse
import com.example.elacosmetologyandroid.repository.network.exception.BaseNetwork
import com.example.elacosmetologyandroid.repository.network.exception.toCompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.utils.EMPTY
import com.example.elacosmetologyandroid.repository.network.utils.validateBody
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.koin.core.inject
import java.io.File


class AuthNetwork : IAuthRepositoryNetwork, BaseNetwork(){

    private val serviceApi: ServiceApi by inject()

    override suspend fun login(email: String, password: String): Pair<User,String> {
        return executeWithConnection {
            val response = serviceApi.login(UserResponse(email,password))
            var data :Pair<User,String>? = null
            if (response.isSuccessful) {
                data = response.validateBody().toLogin()
            }
            data?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }


    override  suspend fun register(register: User): User {
        return executeWithConnection {
            val response = serviceApi.register(UserResponse.toUserResponse(register))
            var user : User? = null
            if (response.isSuccessful && response.body() != null) {
                user = response.validateBody().toUser()
            }
            user?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }

    override suspend fun sendImage(file: File): String {
        return executeWithConnection {
            val image = file.asRequestBody("image/*".toMediaType())
            val multiPartBody = MultipartBody.Part.createFormData("archivo",file.name,image)
            val response = serviceApi.saveProfile(multiPartBody)
            var imageResponse : String? = EMPTY
            if (response.isSuccessful && response.body() != null) {
                imageResponse = response.validateBody().toImage()
            }
            imageResponse?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }

    override suspend fun updateImage(type: String, idUser: String, file: File?): Boolean {
        return executeWithConnection {
            file?.let {
                val image = it.asRequestBody("image/*".toMediaType())
                val multiPartBody = MultipartBody.Part.createFormData("archivo",it.name,image)
                val response = serviceApi.imageProfile(type,idUser,multiPartBody)
                var user : Boolean? = null
                if (response.isSuccessful && response.body() != null) {
                    user = true
                }
                user?: false
            }?:false
        }
    }

    override suspend fun updateImageBanner(type: String, idUser: String, file: File?): Boolean {
        return executeWithConnection {
            file?.let {
                val image = it.asRequestBody("image/*".toMediaType())
                val multiPartBody = MultipartBody.Part.createFormData("archivo",it.name,image)
                val response = serviceApi.imageBanner(type,idUser,multiPartBody)
                var user : Boolean? = null
                if (response.isSuccessful && response.body() != null) {
                    user = true
                }
                user?: false
            }?:false
        }
    }

    override suspend fun loadImage(type: String, idUser: String): Bitmap {
        return executeWithConnection {
            val response = serviceApi.loadImage(type,idUser)
            var bitmap : Bitmap? = null
            if (response.isSuccessful && response.body() != null) {
                response.body()?.let {
                    bitmap = BitmapFactory.decodeStream(it.byteStream())
                }
            }
            bitmap?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }

    override suspend fun updateUser(user: User): User {
        return executeWithConnection {
            val response = serviceApi.updateUser(user.uid,UserResponse.toUserResponse(user))
            var userModel : User? = null
            if (response.isSuccessful && response.body() != null) {
                userModel = response.validateBody().toUser()
            }
            userModel?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }

    override suspend fun deleteAccount(idUser: String): User {
        return executeWithConnection {
            val response = serviceApi.deleteUser(idUser)
            var userModel : User? = null
            if (response.isSuccessful && response.body() != null) {
                userModel = response.validateBody().toUser()
            }
            userModel?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }

    override suspend fun inactiveAccount(idUser: String): User {
        return executeWithConnection {
            val response = serviceApi.inactiveUser(idUser)
            var userModel : User? = null
            if (response.isSuccessful && response.body() != null) {
                userModel = response.validateBody().toUser()
            }
            userModel?: throw response.errorBody()?.toCompleteErrorModel()?.getException() ?: Exception()
        }
    }

}