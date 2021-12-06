package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MusicGeneric(
    @SerializedName("id")
    var id : String = EMPTY,
    @SerializedName("idVideo")
    var idMovie : String = EMPTY,
    @SerializedName("title")
    var title : String = EMPTY,
    @SerializedName("duration")
    var duration :Int = 0,
    @SerializedName("durationTotal")
    var durationTotal :Int = 0
):Parcelable