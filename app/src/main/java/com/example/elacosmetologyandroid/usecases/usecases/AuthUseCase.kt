package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.JsonHelper
import com.example.elacosmetologyandroid.utils.TYPE_USER
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File

class AuthUseCase : KoinComponent {

    private val iAuthRepositoryNetwork: IAuthRepositoryNetwork by inject()
    private val appRepositoryPreference: AppRepositoryPreference by inject()

     suspend fun login(email : String,pass: String):User{
         val response = iAuthRepositoryNetwork.login(email,pass)
         appRepositoryPreference.saveToken(response.second)
         appRepositoryPreference.saveUser(JsonHelper.objectToJSON(response.first).toString())
         return response.first
     }

     suspend fun register(register : User,file: File?):User {
         val responseRegister = iAuthRepositoryNetwork.register(register)
         val response = iAuthRepositoryNetwork.login(responseRegister.email,register.password)
         appRepositoryPreference.saveToken(response.second)
         appRepositoryPreference.saveUser(JsonHelper.objectToJSON(response.first).toString())
         file?.let { iAuthRepositoryNetwork.updateImage(TYPE_USER,response.first.uid,it) }
         return response.first
     }

    fun fetchUser() = JsonHelper.jsonToObject(appRepositoryPreference.getUser(),User::class.java)

    suspend fun updateUser(user : User) : User {
        val response = iAuthRepositoryNetwork.updateUser(user)
        appRepositoryPreference.saveUser(JsonHelper.objectToJSON(response).toString())
        return response
    }

    suspend fun deleteAccount(idUser: String):User{
        val response = iAuthRepositoryNetwork.deleteAccount(idUser)
        appRepositoryPreference.saveToken(EMPTY)
        return response
    }

    suspend fun inactiveAccount(idUser: String):User{
        val response = iAuthRepositoryNetwork.inactiveAccount(idUser)
        appRepositoryPreference.saveToken(EMPTY)
        return response
    }

    suspend fun loadImage(type : String,idUser:String) = iAuthRepositoryNetwork.loadImage(type,idUser)


    suspend fun updateImage(type : String,idUser:String,file: File?) = iAuthRepositoryNetwork.updateImage(type,idUser,file)

    suspend fun updateImageBanner(type : String,idUser:String,file: File?) = iAuthRepositoryNetwork.updateImageBanner(type,idUser,file)

}