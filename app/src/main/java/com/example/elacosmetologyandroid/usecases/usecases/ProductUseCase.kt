package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.usecases.repository.IProductRepositoryNetwork
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProductUseCase : KoinComponent {

    private val iAuthRepositoryNetwork: IProductRepositoryNetwork by inject()

    suspend fun loadListCategory() = iAuthRepositoryNetwork.loadListCategory()


}