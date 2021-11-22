package com.example.elacosmetologyandroid.repository.network.exception

import android.content.Context
import com.example.elacosmetologyandroid.repository.NetworkException
import com.example.elacosmetologyandroid.repository.isAirplaneModeActive
import com.example.elacosmetologyandroid.repository.isConnected
import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseNetwork : KoinComponent {
    private val context: Context by inject()
    suspend fun <T> executeWithConnection(block: suspend () -> T): T {
        if (!context.isConnected() || context.isAirplaneModeActive()) {
            throw NetworkException()
        }
        return block()
    }

    fun <T> executeWithConnectionWithoutSuspend(block: () -> T): T {
        if (!context.isConnected() || context.isAirplaneModeActive()) {
            throw NetworkException()
        }
        return block()
    }
}