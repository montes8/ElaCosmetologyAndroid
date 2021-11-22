package com.example.elacosmetologyandroid.repository.network.exception

import com.example.elacosmetologyandroid.repository.defaultCode
import com.example.elacosmetologyandroid.repository.generalErrorMessage
import com.google.gson.annotations.SerializedName
import java.lang.Exception

data class CompleteErrorModel(
    @SerializedName("titulo")
    val title: String? = "Error",
    @SerializedName("codigo")
    var code: Int? = defaultCode,
    @SerializedName("descripcion")
    val description: String?= generalErrorMessage
) : Exception(description){
    fun getException(): Exception {
        return CompleteErrorModel(this.title, this.code, this.description)
    }
}