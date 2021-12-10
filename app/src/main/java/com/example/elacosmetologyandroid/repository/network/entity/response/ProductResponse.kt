package com.example.elacosmetologyandroid.repository.network.entity.response

import com.example.elacosmetologyandroid.model.ProductModel
import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.gson.annotations.SerializedName

class ProductResponse (
    @SerializedName("uid")
    var id : String?,
    @SerializedName("nombre")
    var name : String?,
    @SerializedName("estado")
    var state : Boolean?,
    @SerializedName("idUsuerio")
    var idUser : String?,
    @SerializedName("precio")
    var price : Double?,
    @SerializedName("puntuacion")
    var rate : Int?,
    @SerializedName("idCategoria")
    var idCategory : String?,
    @SerializedName("descripcion")
    var description : String?,
    @SerializedName("subDescripcion")
    var subDescription : String?,
    @SerializedName("img")
    var img : String?,
    @SerializedName("banner")
    var banner : String?,
    @SerializedName("dias")
    var days : String?,
    @SerializedName("horaInicio")
    var hourStar : String?,
    @SerializedName("horaFin")
    var hourEnd : String?,
    @SerializedName("favorito")
    var favorite : Boolean?
){
    companion object{
        fun toProductResponse(data : ProductModel) = ProductResponse(data.id,data.name,data.state,data.idUser,
            data.price,data.rate,data.idCategory,data.description,data.subDescription,data.img,data.banner,
            data.days,data.hourStar,data.hourEnd,data.favorite
        )

        fun toProductModel(data : ProductResponse) = ProductModel(data.id?: EMPTY,data.name?: EMPTY,
            data.state?: true,data.idUser?: EMPTY, data.price?: 0.0,data.rate?: 0,
            data.idCategory?: EMPTY,data.description?: EMPTY,data.subDescription?: EMPTY,
            data.img?: EMPTY,data.banner?: EMPTY, data.days?: EMPTY,data.hourStar?: EMPTY,
            data.hourEnd?: EMPTY,data.favorite?: false
        )

        fun toListProductModel(response : List<ProductResponse>) = response.map {
            toProductModel(it)
        }
    }
}