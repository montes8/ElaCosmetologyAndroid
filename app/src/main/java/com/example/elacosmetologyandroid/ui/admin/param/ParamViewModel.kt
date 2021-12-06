package com.example.elacosmetologyandroid.ui.admin.param

import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AppUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class ParamViewModel : BaseViewModel(), KoinComponent {

    val successListParamLiveData        : LiveData<List<ParamModel>> get()   = _successListParamLiveData
    private val _successListParamLiveData    = MutableLiveData<List<ParamModel>>()

    val successParamLiveData        : LiveData<ParamModel> get()   = _successParamLiveData
    private val _successParamLiveData    = MutableLiveData<ParamModel>()

    var paramModel : ParamModel = ParamModel()

    private val appUseCase: AppUseCase by inject()


    fun loadParam(){
        executeSuspend {
            val response = appUseCase.loadParam()
            if (response.isNotEmpty())paramModel = response[0]
            _successListParamLiveData.postValue(response)
        }
    }

    fun saveParam(){
        executeSuspend {
            val response = appUseCase.saveParam(paramModel)
             paramModel = response
            _successParamLiveData.postValue(response)
        }
    }

    fun validateParam(title : EditCustomLayout,description : AppCompatEditText){

    }


}