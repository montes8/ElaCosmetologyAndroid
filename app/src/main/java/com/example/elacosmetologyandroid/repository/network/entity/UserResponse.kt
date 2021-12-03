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
    @SerializedName("apellidos")
    var lastName     : String?,
    @SerializedName("correo")
    var email    : String?,
    @SerializedName("password")
    var password : String?,
    @SerializedName("telefono")
    var phone : String?,
    @SerializedName("direccion")
    var address : String?,
    @SerializedName("img")
    var img      : String?,
    @SerializedName("banner")
    var banner      : String?,
    @SerializedName("rol")
    var rol      : String? ,
    @SerializedName("estado")
    var estate     : Boolean?
){
    fun toUser()= User(
        uid = uid?: EMPTY,
        name = name?: EMPTY,
        lastName = lastName?: EMPTY,
        email = email?: EMPTY,
         password= password?: EMPTY,
        phone= phone?: EMPTY,
        address= address?: EMPTY,
        img = img?: EMPTY,
        banner = banner?: EMPTY,
        rol = rol?: EMPTY,
        estate = estate?: false)

    companion object{
        fun toUserResponse(user: User)= UserResponse(
            user.uid,
            user.name,
            user.lastName,
            user.email,
            user.password,
            user.phone,
            user.address,
            user.img,
            user.banner,
            USER_ROLE,true)

    }

   constructor(email: String,password: String) : this("","","",email,password,"","","","","",false)

}