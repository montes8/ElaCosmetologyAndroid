package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.ID_DEFAULT
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
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
    var idCategory : String = ID_DEFAULT,
    @SerializedName("idProducto")
    var idProduct :  String = ID_DEFAULT
):Parcelable