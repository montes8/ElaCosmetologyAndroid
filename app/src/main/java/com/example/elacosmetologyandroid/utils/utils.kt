package com.example.elacosmetologyandroid.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

inline fun <reified T> getData(context: Context, fileName: String): T {
    val jsonData = Gson()
    val json = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return jsonData.fromJson(json, object : TypeToken<T>() {}.type)
}