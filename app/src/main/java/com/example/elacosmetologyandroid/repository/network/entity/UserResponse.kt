package com.example.elacosmetologyandroid.repository.network.entity

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.USER_ROLE
import com.google.gson.annotations.SerializedName

class UserResponse (
    @SerializedName("nombre")
    var name     : String,
    @SerializedName("correo")
    var email    : String,
    @SerializedName("password")
    var password : String?,
    @SerializedName("img")
    var img      : String?,
    @SerializedName("rol")
    var rol      : String? ,
    @SerializedName("estado")
    var estate     : Boolean?
){
    fun toUser()= User(name = name,
        email = email,
         password= password?: EMPTY,
        img = img?: EMPTY,
        rol = rol?: EMPTY,
        estate = estate?: false)

    companion object{
        fun toUserResponse(user: User)= UserResponse(user.name,user.email,user.password,user.img,USER_ROLE,true)
    }

 constructor(name: String,password: String) : this("",name,password,"","",false)

}