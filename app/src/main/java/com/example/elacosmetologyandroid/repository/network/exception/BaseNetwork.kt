package com.example.elacosmetologyandroid.repository.network.exception

import android.content.Context
import com.example.elacosmetologyandroid.repository.network.utils.isAirplaneModeActive
import com.example.elacosmetologyandroid.repository.network.utils.isConnected
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

}