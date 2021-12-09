package com.example.elacosmetologyandroid.model

import com.example.elacosmetologyandroid.repository.network.utils.EMPTY

class CategoryModel(
     var name : String = EMPTY,
     var description : String= EMPTY,
     var state : Boolean= true,
     var recommend : Boolean= false,
     var idUser : String= EMPTY,
)