package com.example.elacosmetologyandroid.usecases.repository

import com.example.elacosmetologyandroid.model.CategoryModel
import com.example.elacosmetologyandroid.model.ProductModel


interface IProductRepositoryNetwork {

     suspend fun loadListCategory():List<CategoryModel>

     suspend fun saveCategory(category : CategoryModel,id : String) : CategoryModel

     suspend fun loadListProduct() : List<ProductModel>
}