package com.example.elacosmetologyandroid.repository.network.mapper

import com.example.elacosmetologyandroid.repository.network.exception.CompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.exception.GenericException
import com.example.elacosmetologyandroid.repository.network.exception.NetworkException
import com.example.elacosmetologyandroid.repository.network.exception.UnAuthorizedException
import com.google.gson.Gson
import okhttp3.Headers
import okhttp3.ResponseBody
import retrofit2.Response
import java.net.UnknownHostException

sealed class MapperResponse<T> {


    data class Success<T>(val data: T) : MapperResponse<T>()

    sealed class Failure<T> {

        data class Error<T>(val response: Response<T>) : MapperResponse<T>() {
            val headers: Headers = response.headers()
            val raw: okhttp3.Response = response.raw()
            val errorBody: ResponseBody? = response.errorBody()

        }

        data class Exception<T>(val exception: Throwable) : MapperResponse<T>() {
            val message: String? = exception.localizedMessage
            override fun toString(): String = "[ApiResponse.Failure.Exception](message=$message)"
        }
    }

    companion object {

        @JvmStatic
        fun <T> Throwable.toApiResponse(): MapperResponse<T> {
            return Failure.Exception(this)
        }

        @JvmStatic
        fun <T> from(response: Response<T>): MapperResponse<T> {
            return if (response.isSuccessful && response.body() != null) {
                val body = response.body()
                if (body != null) {
                    Success(body)
                } else {
                    Failure.Exception(Exception("Not found Payload"))
                }
            } else {
                Failure.Error(response)
            }
        }
    }
}


fun <T> MapperResponse<T>.getResultOrThrowException(): T = when (this) {
    is MapperResponse.Success -> data

    is MapperResponse.Failure.Exception -> {
        when (exception) {
            is UnknownHostException -> throw NetworkException()
            else -> throw GenericException()
        }
    }

    is MapperResponse.Failure.Error -> {
        if (errorBody != null) {
            var completeErrorModel: CompleteErrorModel? = null
            try {
                completeErrorModel = Gson().fromJson(errorBody.string(), CompleteErrorModel::class.java)

            } catch (e: Exception) {
                throw GenericException()
            }

            if (completeErrorModel != null) {
                throw completeErrorModel.getApiException()
            } else {
                when (response.code()) {
                    404 -> throw NetworkException()
                    407 -> throw UnAuthorizedException()
                    else -> throw GenericException()
                }
            }
        } else {
            throw GenericException()
        }
    }
}