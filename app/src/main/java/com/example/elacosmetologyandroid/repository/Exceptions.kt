package com.example.elacosmetologyandroid.repository

class UserNotFoundException(mgs: String = "") : Exception(mgs)

class ApiException(val code: Int, val mMessage: String, val title: String) : Exception()

class GenericException : Exception()

class NetworkException : Exception()

class NotFoundException : Exception()

class UnAuthorizedException : Exception()
