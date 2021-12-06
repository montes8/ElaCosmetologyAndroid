package com.example.elacosmetologyandroid.repository.network.entity.response

import com.example.elacosmetologyandroid.model.ParamModel
import com.google.gson.annotations.SerializedName

class ParamResponse (
    @SerializedName("uid")
    var id : String,
    @SerializedName("titulo")
    var title : String,
    @SerializedName("descripcion")
    var description : String,
    @SerializedName("registro")
    var enableRegister : Boolean
){
    companion object{
        fun toListParamR(response : List<ParamResponse>)=response.map {
            ParamModel(it.id,it.title,it.description,it.enableRegister)
        }

        fun toParamResponse(data : ParamModel)=ParamResponse(
            data.id,data.title,data.description,data.enableRegister
        )

        fun toParamModel(data : ParamResponse)=ParamModel(
            data.id,data.title,data.description,data.enableRegister
        )
    }
}