package com.example.elacosmetologyandroid.repository.network.entity

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.EMPTY
import com.google.gson.annotations.SerializedName

class UserResponse (
    @SerializedName("nombre")
    var name     : String?,
    @SerializedName("correo")
    var email    : String?,
    @SerializedName("password")
    var password : String?,
    @SerializedName("img")
    var img      : String?,
    @SerializedName("rol")
    var rol      : String?,
    @SerializedName("estado")
    var estate     : Boolean?
){
    fun toUser()= User(name = name?: EMPTY,
        email = email?: EMPTY,
         password= password?: EMPTY,
        img = img?: EMPTY,
        rol = name?: EMPTY,
        estate = estate?: false,)
}