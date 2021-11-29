package com.example.elacosmetologyandroid.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

object JsonHelper {
    fun objectToJSON(obj: Any?): JSONObject? {
        val jsonCustom = GsonBuilder()
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(jsonCustom.create().toJson(obj))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return jsonObject
    }

    fun <T> jsonToObject(json: String?, cls: Class<T>?): T? {
        val jsonCustom = GsonBuilder()
        return jsonCustom.create().fromJson(json, cls)
    }

    fun <T> jsonToObjectList(json: String?): T? {
        val jsonData = Gson()
        return jsonData.fromJson(json, object : TypeToken<T>() {}.type)
    }
}