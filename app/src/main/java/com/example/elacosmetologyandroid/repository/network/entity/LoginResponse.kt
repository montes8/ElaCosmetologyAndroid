package com.example.elacosmetologyandroid.repository.network.entity

import com.google.gson.annotations.SerializedName

class LoginResponse(
    @SerializedName("token")
    var token :String,
    @SerializedName("usuario")
    var user : UserResponse
){
    fun toLogin() = Pair(user.toUser(),token)
}