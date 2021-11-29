package com.example.elacosmetologyandroid.usecases.repository

interface AppRepositoryPreference {

    @Throws(Exception::class)
     fun getToken(): String

    @Throws(Exception::class)
    fun saveToken(value: String)

    @Throws(Exception::class)
    fun getUser(): String

    @Throws(Exception::class)
     fun saveUser(value: String)

    @Throws(Exception::class)
    fun getListMusic(): String

    @Throws(Exception::class)
    fun saveListMusic(value: String)
}