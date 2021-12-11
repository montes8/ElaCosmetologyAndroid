package com.example.elacosmetologyandroid.ui.admin.addcategory

import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.model.CategoryModel
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.ProductUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class CategoryViewModel : BaseViewModel(), KoinComponent {

    val successCategoryLiveData        : LiveData<CategoryModel> get()   = _successCategoryLiveData
    private val _successCategoryLiveData    = MutableLiveData<CategoryModel>()

    private val productUseCase: ProductUseCase by inject()
    private var categoryModel : CategoryModel = CategoryModel()

    val stateObserver = ObservableBoolean(true)
    val recommendObserver = ObservableBoolean(false)


    fun saveCategory(btn : ProgressButton){
            btn.isButtonLoading = true
            executeSuspendNotProgress {
                val response = productUseCase.saveCategory(categoryModel)
                _successCategoryLiveData.postValue(response)
            }
    }

    fun validateCategory(name : EditCustomLayout, description : AppCompatEditText, btnCategory : ProgressButton){
        btnCategory.isButtonEnabled = name.uiText.isNotEmpty() && description.text.toString().isNotEmpty()
        categoryModel.name = name.uiText
        categoryModel.description = description.text.toString()
        categoryModel.state = stateObserver.get()
        categoryModel.recommend = recommendObserver.get()
    }

}