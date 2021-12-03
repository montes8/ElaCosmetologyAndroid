package com.example.elacosmetologyandroid.repository.network.entity

import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.gson.annotations.SerializedName

class ImageResponse(
    @SerializedName("nombre")
    var nameImage : String?
){
    fun toImage() = nameImage?: EMPTY
}