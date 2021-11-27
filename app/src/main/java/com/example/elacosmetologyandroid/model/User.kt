package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.utils.EMPTY
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid      : String = EMPTY,
    var name     : String = EMPTY,
    var email    : String = EMPTY,
    var password : String = EMPTY,
    var img      : String = EMPTY,
    var rol      : String = EMPTY,
    var estate     : Boolean = false

):Parcelable