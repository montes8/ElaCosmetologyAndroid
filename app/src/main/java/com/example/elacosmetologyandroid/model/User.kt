package com.example.elacosmetologyandroid.model

import android.os.Parcelable
import com.example.elacosmetologyandroid.utils.EMPTY
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid: String = EMPTY,
    var name: String = EMPTY,
    var lastName: String = EMPTY,
    var email: String = EMPTY,
    var password: String = EMPTY,
    var phone: String = EMPTY,
    var address: String = EMPTY,
    var img: String = EMPTY,
    var banner: String = EMPTY,
    var rol: String = EMPTY,
    var estate: Boolean = false

) : Parcelable