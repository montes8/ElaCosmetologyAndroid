package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.utils.EMPTY
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VideoModel(
    var id : String = EMPTY,
    var idMovie : String = EMPTY,
    var author : String = EMPTY,
    var nameVideo : String = EMPTY,
    var description : String = EMPTY,
    var duration :Int = 0,
    var durationTotal :Int = 0
):Parcelable