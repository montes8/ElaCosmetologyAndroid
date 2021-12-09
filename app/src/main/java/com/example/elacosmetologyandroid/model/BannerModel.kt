package com.example.elacosmetologyandroid.model

import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.gson.annotations.SerializedName

data class BannerModel(
    @SerializedName("id")
    var id : String = EMPTY,
    @SerializedName("titulo")
    var title : String= EMPTY,
    @SerializedName("descripcion")
    var description : String= EMPTY,
    @SerializedName("img")
    var img : String= EMPTY,
    @SerializedName("idCategoria")
    var idCategory : String = EMPTY
){
    companion object{
        fun toListModelGeneric(response : List<BannerModel>) = response.map {
            BannerModel(it.id,it.title,it.description,it.img,it.idCategory)
        }
    }
}