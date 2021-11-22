package com.example.elacosmetologyandroid.repository

import com.example.elacosmetologyandroid.repository.network.exception.CompleteErrorModel
import com.google.gson.Gson
import okhttp3.ResponseBody

fun ResponseBody?.toCompleteErrorModel() : CompleteErrorModel? {
    return this?.let {
        return Gson().fromJson(it.string(), CompleteErrorModel::class.java)
    } ?: CompleteErrorModel()
}