package com.example.elacosmetologyandroid.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PlaceModel(
    var name: String,
    var location: LocationModel,
    var id: String = "",
    var reference: String = "",
    var interiorFloor: String = "",
    var district: String = "",
    var province: String = ""
) : Parcelable