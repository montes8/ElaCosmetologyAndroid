package com.example.elacosmetologyandroid.ui.login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.extensions.isEmailValid
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.core.KoinComponent
import org.koin.core.inject


class LoginViewModel : BaseViewModel(), KoinComponent {



    var enableButton = MutableLiveData(false)
    var loadingButton = MutableLiveData(false)


    private val authUseCase: AuthUseCase by inject()



    fun login(pass : EditCustomLayout){
        if (validateEmail(pass)){
            executeSuspend {

            }
        }
    }


    fun validateLogin(email : EditCustomLayout, pass : EditCustomLayout){
        pass.uiErrorMessage = EMPTY
            enableButton.postValue(!TextUtils.isEmpty(email.uiText) && !TextUtils.isEmpty(pass.uiText))
    }

    private fun validateEmail(pass : EditCustomLayout):Boolean{
        if (!isEmailValid(pass.uiText)){
            pass.uiErrorMessage = "El correo ingresado no es valido"
            return false
        }
        return true
    }

}