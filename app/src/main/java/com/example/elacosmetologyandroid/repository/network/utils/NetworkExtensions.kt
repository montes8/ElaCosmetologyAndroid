package com.example.elacosmetologyandroid.repository.network.utils

import retrofit2.Response


fun <T> Response<T>.validateBody() : T {
    this.body()?.let {
        return it
    } ?: throw NullPointerException()
}