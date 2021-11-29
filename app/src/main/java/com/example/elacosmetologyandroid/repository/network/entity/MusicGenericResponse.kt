package com.example.elacosmetologyandroid.repository.network.entity

import com.example.elacosmetologyandroid.model.MusicGeneric
import com.google.gson.annotations.SerializedName

class MusicGenericResponse (
    @SerializedName("id")
    var idMusic : String?,
    @SerializedName("title")
    var title : String?,
    @SerializedName("duration")
    var duration :Int?,
    @SerializedName("durationTotal")
    var durationTotal :Int?
){
    companion object{
        fun toListMusic(response: List<MusicGenericResponse>) = response.map {
            MusicGeneric(
                id = it.idMusic?:"SRt0KAMCI4Q",
                title = it.title?:"tini",
                duration = it.duration?:0,
                durationTotal = it.durationTotal?:0,
            )
        }
    }
}