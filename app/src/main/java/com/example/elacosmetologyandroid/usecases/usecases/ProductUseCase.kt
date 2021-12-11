package com.example.elacosmetologyandroid.usecases.usecases

import com.example.elacosmetologyandroid.model.CategoryModel
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.usecases.repository.AppRepositoryPreference
import com.example.elacosmetologyandroid.usecases.repository.IProductRepositoryNetwork
import com.example.elacosmetologyandroid.utils.ID_DEFAULT
import com.example.elacosmetologyandroid.utils.JsonHelper
import org.koin.core.KoinComponent
import org.koin.core.inject

class ProductUseCase : KoinComponent {

    private val iAuthRepositoryNetwork: IProductRepositoryNetwork by inject()
    private val appRepositoryPreference: AppRepositoryPreference by inject()

    suspend fun loadListCategory() = iAuthRepositoryNetwork.loadListCategory()

    suspend fun saveCategory(category : CategoryModel):CategoryModel {
         val user = JsonHelper.jsonToObject(appRepositoryPreference.getUser(), User::class.java)
        return  iAuthRepositoryNetwork.saveCategory(category,user?.uid?: ID_DEFAULT)
    }

    suspend fun loadListProduct() = iAuthRepositoryNetwork.loadListProduct()




}