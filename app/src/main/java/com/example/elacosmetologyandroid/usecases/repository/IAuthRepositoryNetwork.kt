package com.example.elacosmetologyandroid.usecases.repository

import android.graphics.Bitmap
import com.example.elacosmetologyandroid.model.User
import java.io.File


interface IAuthRepositoryNetwork {

     suspend fun login(email : String, password : String): Pair<User, String>

     suspend fun register(register : User):User

     suspend fun sendImage(file : File):String

     suspend fun sendImageProfile(type : String,idUser:String,file : File):Boolean

     suspend fun loadImage(type : String,idUser:String):Bitmap
}