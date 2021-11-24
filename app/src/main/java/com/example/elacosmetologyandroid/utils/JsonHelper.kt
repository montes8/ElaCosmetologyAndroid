package com.example.elacosmetologyandroid.utils

import com.google.gson.GsonBuilder
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

    fun <T> jsonToObject(json: String?, cls: Class<T>?): T {
        val jsonCustom = GsonBuilder()
        return jsonCustom.create().fromJson(json, cls)
    }
}