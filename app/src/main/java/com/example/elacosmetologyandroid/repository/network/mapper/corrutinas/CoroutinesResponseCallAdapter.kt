package com.example.elacosmetologyandroid.repository.network.mapper.corrutinas

import com.example.elacosmetologyandroid.repository.network.mapper.MapperResponse
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ApiResponseCallAdapter<T : Any>(
    private val resultType: Type
) : CallAdapter<T, Call<MapperResponse<T>>> {

    override fun responseType(): Type = resultType
    override fun adapt(call: Call<T>): Call<MapperResponse<T>> {
        return CustomApiResponseCall(call, resultType)
    }
}