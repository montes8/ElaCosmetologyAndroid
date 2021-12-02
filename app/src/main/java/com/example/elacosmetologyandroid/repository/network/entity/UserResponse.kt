package com.example.elacosmetologyandroid.repository.network.entity

import android.graphics.Bitmap
import android.opengl.ETC1.encodeImage
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.USER_ROLE
import com.example.elacosmetologyandroid.utils.encodeImageConverter
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
        rol = rol?: EMPTY,
        estate = estate?: false)

    companion object{
        fun toUserResponse(user: User,imgProfile : Bitmap?)= UserResponse(
            user.uid,
            user.name,
            user.lastName,
            user.email,
            user.password,
            user.phone,
            user.address,
           converterBitmap(imgProfile),
            USER_ROLE,true)

        private fun converterBitmap(imageProfile : Bitmap?):String{
            return imageProfile?.let {
                encodeImageConverter(it)
            }?:EMPTY
        }
    }

   constructor(email: String,password: String) : this("","","",email,password,"","","","",false)

}