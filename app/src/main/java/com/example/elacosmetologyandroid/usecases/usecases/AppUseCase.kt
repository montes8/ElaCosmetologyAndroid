package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import com.example.elacosmetologyandroid.usecases.repository.IAuthRepositoryNetwork
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.core.KoinComponent
import org.koin.core.inject

class AppUseCase : KoinComponent {

    private val iAuthRepositoryNetwork: IAuthRepositoryNetwork by inject()

    private val appRepositoryPreference: AppRepositoryPreference by inject()

    suspend fun session() : Boolean{
        val parameter = iAuthRepositoryNetwork.loadListMusic()
        UserTemporary.listMusic = parameter
        return appRepositoryPreference.getToken().isNotEmpty()
    }

    fun logout() = appRepositoryPreference.saveToken(EMPTY)

    suspend fun loadBanner() = iAuthRepositoryNetwork.loadBanner()

}