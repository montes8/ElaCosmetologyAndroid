package com.example.elacosmetologyandroid.repository.network.exception

import com.example.elacosmetologyandroid.repository.network.utils.defaultCode
import com.example.elacosmetologyandroid.repository.network.utils.generalErrorMessage
import com.google.gson.annotations.SerializedName
import java.lang.Exception

data class CompleteErrorModel(
    @SerializedName("errorCode")
    var code: Int? = defaultCode,
    @SerializedName("title")
    val title: String? = "Error General",
    @SerializedName("description")
    val description: String?= generalErrorMessage
) : Exception(description){
    fun getException(): Exception {
        return CompleteErrorModel( this.code, this.title,this.description)
    }
}