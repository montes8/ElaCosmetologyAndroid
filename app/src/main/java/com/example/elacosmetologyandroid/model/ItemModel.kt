package com.example.elacosmetologyandroid.model

import com.google.gson.annotations.SerializedName

class ItemModel(
    @SerializedName("id")
    var id : Int,
    @SerializedName("title")
    var title : String,
    @SerializedName("icon")
    var icon : String
)