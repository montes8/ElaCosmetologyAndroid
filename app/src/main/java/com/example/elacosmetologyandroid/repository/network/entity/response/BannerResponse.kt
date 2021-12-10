package com.example.elacosmetologyandroid.repository.network.entity.response

import com.example.elacosmetologyandroid.model.BannerModel
import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.gson.annotations.SerializedName

class BannerResponse(
    @SerializedName("uid")
    var id: String?,
    @SerializedName("titulo")
    var title: String?,
    @SerializedName("descripcion")
    var description: String?,
    @SerializedName("img")
    var img: String?,
    @SerializedName("categoria")
    var idCategory: String?,
    @SerializedName("idProducto")
    var idProduct: String?
){
    companion object {
        fun toBannerResponse(banner: BannerModel) = BannerResponse(
            id = banner.id,
            title = banner.title,
            description = banner.description,
            img = banner.img,
            idCategory = banner.idCategory,
            idProduct = banner.idProduct
        )

         fun toBannerModel(banner: BannerResponse) = BannerModel(
            banner.id ?: EMPTY, banner.title ?: EMPTY,
            banner.description ?: EMPTY, banner.img ?: EMPTY,
            banner.idCategory ?: EMPTY,banner.idProduct?: EMPTY
        )

        fun toListBannerModel(response: List<BannerResponse>) = response.map {
            toBannerModel(it)
        }
    }
}