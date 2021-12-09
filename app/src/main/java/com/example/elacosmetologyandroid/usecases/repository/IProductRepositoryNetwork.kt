package com.example.elacosmetologyandroid.usecases.repository

import com.example.elacosmetologyandroid.model.CategoryModel


interface IProductRepositoryNetwork {

     suspend fun loadListCategory():List<CategoryModel>
}