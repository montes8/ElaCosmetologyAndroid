package com.example.elacosmetologyandroid.repository.network.api

import com.example.elacosmetologyandroid.model.CategoryModel
import com.example.elacosmetologyandroid.repository.network.ServiceApi
import com.example.elacosmetologyandroid.repository.network.entity.response.CategoryResponse
import com.example.elacosmetologyandroid.repository.network.exception.BaseNetwork
import com.example.elacosmetologyandroid.repository.network.utils.validateBody
import com.example.elacosmetologyandroid.usecases.repository.IProductRepositoryNetwork
import org.koin.core.inject


class ProductNetwork : IProductRepositoryNetwork, BaseNetwork(){

    private val serviceApi: ServiceApi by inject()
    
    override suspend fun loadListCategory(): List<CategoryModel> {
        return executeWithConnection {
            val response = serviceApi.loadCategory()
            var data :List<CategoryModel>? = null
            if (response.isSuccessful) {

                data = CategoryResponse.toListCategory(response.validateBody())
            }
            data?: ArrayList()
        }
    }

}