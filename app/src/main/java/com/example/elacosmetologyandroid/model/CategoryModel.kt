package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.repository.network.utils.EMPTY
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryModel(
     var name : String = EMPTY,
     var description : String= EMPTY,
     var state : Boolean= true,
     var recommend : Boolean= false,
     var idUser : String= EMPTY,
):Parcelable{
     override fun toString() = name
}