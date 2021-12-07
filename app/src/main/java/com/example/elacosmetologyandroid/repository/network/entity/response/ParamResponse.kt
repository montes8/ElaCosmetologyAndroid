package com.example.elacosmetologyandroid.repository.network.entity.response

import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.gson.annotations.SerializedName

class ParamResponse (
    @SerializedName("uid")
    var id : String?,
    @SerializedName("titulo")
    var title : String?,
    @SerializedName("descripcion")
    var description : String?,
    @SerializedName("registro")
    var enableRegister : Boolean?
){
    companion object{
        fun toParamResponse(data : ParamModel)=ParamResponse(
            data.id,data.title,data.description,data.enableRegister
        )

        fun toParamModel(data : ParamResponse?)=ParamModel(
            data?.id?: EMPTY,data?.title?:"Bienvedida, ",data?.description?: "Aquí encontraras algunos videos referenciales de algunos establecimientos",data?.enableRegister?:true
        )
    }
}