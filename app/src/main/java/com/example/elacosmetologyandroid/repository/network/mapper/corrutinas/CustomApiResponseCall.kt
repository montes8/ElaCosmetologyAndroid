package com.example.elacosmetologyandroid.repository.network.mapper.corrutinas

import com.example.elacosmetologyandroid.repository.network.mapper.MapperResponse
import com.example.elacosmetologyandroid.repository.network.mapper.MapperResponse.Companion.toApiResponse
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class CustomApiResponseCall<T : Any>(
    private val backingCall: Call<T>,
    private val resultType: Type,
) : Call<MapperResponse<T>> {

    override fun enqueue(callback: Callback<MapperResponse<T>>) {
        backingCall.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val apiResponse = MapperResponse.from(response)
                callback.onResponse(this@CustomApiResponseCall, Response.success(apiResponse))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val apiResponse = t.toApiResponse<T>()
                callback.onResponse(this@CustomApiResponseCall, Response.success(apiResponse))
            }

        })

    }

    override fun execute(): Response<MapperResponse<T>> {
        throw UnsupportedOperationException("CustomApiResponseCall does not support synchronous execution")
    }

    override fun isExecuted() = synchronized(this) {
        backingCall.isExecuted
    }

    override fun clone(): Call<MapperResponse<T>> = CustomApiResponseCall(
        backingCall,
        resultType
    )

    override fun cancel() = synchronized(this) {
        backingCall.cancel()
    }

    override fun isCanceled() = synchronized(this) {
        backingCall.isCanceled
    }

    override fun request(): Request? = backingCall.request()
    override fun timeout(): Timeout? = backingCall.timeout()

}