package com.example.elacosmetologyandroid.repository.network.exception

import com.google.gson.Gson
import okhttp3.ResponseBody

fun ResponseBody?.toCompleteErrorModel(code : Int) : CompleteErrorModel? {
    return this?.let {
        return  if (code == 407) throw UnAuthorizedException () else Gson().fromJson(it.string(), CompleteErrorModel::class.java)
    } ?: CompleteErrorModel()
}