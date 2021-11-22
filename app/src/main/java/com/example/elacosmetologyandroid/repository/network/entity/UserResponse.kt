package com.example.elacosmetologyandroid.repository.network.entity

import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.repository.EMPTY

class UserResponse (
    var name     : String?,
    var email    : String?,
    var password : String?,
    var img      : String?,
    var rol      : String?,
    var estate     : Boolean?
){
    fun toUser()= User(name = name?: EMPTY,
        email = email?: EMPTY,
         password= password?: EMPTY,
        img = img?: EMPTY,
        rol = name?: EMPTY,
        estate = estate?: false,)
}