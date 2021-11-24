package com.example.elacosmetologyandroid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.extensions.isEmailValid
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.core.KoinComponent
import org.koin.core.inject


class AppViewModel : BaseViewModel(), KoinComponent {


    val successSessionLiveData        : LiveData<Boolean> get()   = _successSessionLiveData
    private val _successSessionLiveData    = MutableLiveData<Boolean>()


    private val authUseCase: AuthUseCase by inject()



    fun session(){
            executeSuspend {
                val response = authUseCase.session()
                _successSessionLiveData.postValue(response)
            } }
}