package com.example.elacosmetologyandroid.repository.network.exception

import com.example.elacosmetologyandroid.utils.EMPTY


data class ApiException(val code: Int = 0, val mMessage: String = EMPTY): Exception()

class GenericException() : Exception()

class NetworkException() : Exception()

class UnAuthorizedException() : Exception()
