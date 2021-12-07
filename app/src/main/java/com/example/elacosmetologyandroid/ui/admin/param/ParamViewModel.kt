package com.example.elacosmetologyandroid.ui.admin.param

import android.widget.EditText
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isNotEmpty
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.button.ProgressButton
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

    val registerObserver = ObservableBoolean(true)
    var paramModel : ParamModel = ParamModel()

    private val appUseCase: AppUseCase by inject()


    fun loadParam(){
        executeSuspend{
            val response = appUseCase.loadParam()
            if (response.isNotEmpty())configDefault(response[0])
            _successListParamLiveData.postValue(response)
        }
    }

    fun saveParam(btnParam : ProgressButton){
        btnParam.isButtonLoading = true
        executeSuspend {
            val response = appUseCase.saveParam(paramModel)
            configDefault(response)
            _successParamLiveData.postValue(response)
        }
    }

    fun updateParam(btnParam : ProgressButton){
        btnParam.isButtonLoading = true
        executeSuspend {
            val response = appUseCase.updateParam(paramModel)
            configDefault(response)
            _successParamLiveData.postValue(response)
        }
    }

    private fun configDefault(param : ParamModel){
        paramModel = param
        registerObserver.set(paramModel.enableRegister)
    }

    fun validateParam(title : EditCustomLayout,description : AppCompatEditText,btnParam : ProgressButton){
        btnParam.isButtonEnabled = title.isNotEmpty() && description.text.toString().isNotEmpty()
        paramModel.title = title.uiText
        paramModel.description = description.text.toString()
        paramModel.enableRegister = registerObserver.get()
    }


}