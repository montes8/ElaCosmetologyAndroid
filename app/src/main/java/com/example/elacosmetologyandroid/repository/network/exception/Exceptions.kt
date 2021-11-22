package com.example.elacosmetologyandroid.repository


data class ApiException(val code: Int = 0, val mMessage: String = EMPTY): Exception()

class GenericException() : Exception()

class NetworkException() : Exception()

class UnAuthorizedException() : Exception()
