package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.utils.EMPTY
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductModel (
    var id : String = EMPTY,
    var name : String= EMPTY,
    var state : Boolean = true,
    var idUser : String= EMPTY,
    var price : Double= 0.0,
    var rate : Int= 0,
    var idCategory : String= EMPTY,
    var description : String= EMPTY,
    var subDescription : String= EMPTY,
    var img : String= EMPTY,
    var banner : String= EMPTY,
    var days : String= EMPTY,
    var hourStar : String= EMPTY,
    var hourEnd : String= EMPTY,
    var favorite : Boolean= false
    ):Parcelable