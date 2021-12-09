package com.example.elacosmetologyandroid.repository.network.entity.response

import com.example.elacosmetologyandroid.model.CategoryModel
import com.example.elacosmetologyandroid.repository.network.utils.EMPTY
import com.google.gson.annotations.SerializedName

class CategoryResponse(
    @SerializedName("nombre")
    var name : String?,
    @SerializedName("descripcion")
    var description : String?,
    @SerializedName("estado")
    var state : Boolean?,
    @SerializedName("recomendado")
    var recommend : Boolean?,
    @SerializedName("isUsuario")
    var idUser : String?,

    ) {

    companion object{

        fun toCategory(categoryResponse : CategoryResponse)= CategoryModel(
            categoryResponse.name?: EMPTY,
            categoryResponse.description?: EMPTY,
            categoryResponse.state?:true,
            categoryResponse.recommend?:false,
            categoryResponse.idUser?: EMPTY
        )

        fun toListCategory(response : List<CategoryResponse>)= response?.map {
            toCategory(it)
        }
    }
}