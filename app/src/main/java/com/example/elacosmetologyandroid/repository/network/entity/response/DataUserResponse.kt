package com.example.elacosmetologyandroid.repository.network.entity.response

import com.example.elacosmetologyandroid.repository.network.entity.response.UserResponse
import com.google.gson.annotations.SerializedName

class DataUserResponse(
    @SerializedName("token")
    var token :String,
    @SerializedName("usuario")
    var user : UserResponse
){
    fun toLogin() = Pair(user.toUser(),token)

    fun toUser() = user.toUser()
}