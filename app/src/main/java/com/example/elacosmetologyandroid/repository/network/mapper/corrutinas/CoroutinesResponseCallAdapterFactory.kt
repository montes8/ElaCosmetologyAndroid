@file:Suppress("unused")

package com.example.elacosmetologyandroid.repository.network.mapper.corrutinas

import com.example.elacosmetologyandroid.repository.network.mapper.MapperResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class CoroutinesResponseCallAdapterFactory private constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {


       return when (getRawType(returnType)) {
            Call::class.java -> {

                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {

                    MapperResponse::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        // CoroutinesResponseCallAdapter(resultType)
                        ApiResponseCallAdapter<Any>(resultType)
                    }
                    else -> null
                }
            }
            else -> null
        }

    }

    companion object {
        @JvmStatic
        fun create(): CoroutinesResponseCallAdapterFactory =
            CoroutinesResponseCallAdapterFactory()
    }
}
