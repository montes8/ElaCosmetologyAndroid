package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.utils.EMPTY
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name     : String,
    var email    : String,
    var password : String,
    var img      : String = EMPTY,
    var rol      : String = EMPTY,
    var estate     : Boolean = false

):Parcelable