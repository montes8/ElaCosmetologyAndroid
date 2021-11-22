package com.example.elacosmetologyandroid.usecases.repository

interface AppRepositoryPreference {

    @Throws(Exception::class)
    fun getLogin(): Boolean

    @Throws(Exception::class)
    fun saveLogin(value: Boolean)

    @Throws(Exception::class)
    fun getToken(): String

    @Throws(Exception::class)
    fun saveToken(value: String)
}