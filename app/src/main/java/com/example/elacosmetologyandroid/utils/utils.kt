package com.example.elacosmetologyandroid.utils

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

inline fun <reified T> getData(context: Context, fileName: String): T {
    val jsonData = Gson()
    val json = context.assets.open(fileName).bufferedReader().use {
        it.readText()
    }
    return jsonData.fromJson(json, object : TypeToken<T>() {}.type)
}

fun encodeImageConverter(bm: Bitmap): String {
    val byte= ByteArrayOutputStream()
    bm.compress(Bitmap.CompressFormat.JPEG, 100, byte)
    val b = byte.toByteArray()
    return Base64.encodeToString(b, Base64.DEFAULT)
}