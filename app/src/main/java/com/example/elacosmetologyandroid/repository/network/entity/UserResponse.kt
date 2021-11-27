package com.example.elacosmetologyandroid.repository.network.entity

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.USER_ROLE
import com.google.gson.annotations.SerializedName

class UserResponse (
    @SerializedName("uid")
    var uid     : String?,
    @SerializedName("nombre")
    var name     : String?,
    @SerializedName("correo")
    var email    : String?,
    @SerializedName("password")
    var password : String?,
    @SerializedName("img")
    var img      : String?,
    @SerializedName("rol")
    var rol      : String? ,
    @SerializedName("estado")
    var estate     : Boolean?
){
    fun toUser()= User(
        uid = uid?: EMPTY,
        name = name?: EMPTY,
        email = email?: EMPTY,
         password= password?: EMPTY,
        img = img?: EMPTY,
        rol = rol?: EMPTY,
        estate = estate?: false)

    companion object{
        fun toUserResponse(user: User)= UserResponse(user.uid,user.name,user.email,user.password,user.img,USER_ROLE,true)
    }

   constructor(email: String,password: String) : this("","",email,password,"","",false)

}