package com.example.elacosmetologyandroid.repository.network.entity

import com.example.elacosmetologyandroid.model.VideoModel
import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.gson.annotations.SerializedName

class VideoResponse (
    @SerializedName("id")
    var id : String?,
    @SerializedName("idVideo")
    var idMovie : String?,
    @SerializedName("autor")
    var author : String?,
    @SerializedName("nombreVideo")
    var nameVideo : String?,
    @SerializedName("descripcion")
    var description : String?,
    @SerializedName("duracion")
    var duration :Int?,
    @SerializedName("duracionTotal")
    var durationTotal :Int?
){
    companion object{
        fun toListVideo(response: List<VideoResponse>) = response.map {
           toVideo(it)
        }

        fun toVideoResponse(video : VideoModel) =
            VideoResponse(
                id = video.id,
                idMovie = video.idMovie,
                author = video.author,
                nameVideo = video.nameVideo,
                description = video.description,
                duration = video.duration,
                durationTotal = video.durationTotal,
            )

        fun toVideo(response: VideoResponse) =
            VideoModel(
                id = response.id?: EMPTY,
                idMovie = response.idMovie?:"SRt0KAMCI4Q",
                author = response.author?:"tini",
                nameVideo = response.nameVideo?: "Sueltate el pelo",
                description = response.description?:"Este es el video por default",
                duration = response.duration?:0,
                durationTotal = response.durationTotal?:0,
            )

    }
}