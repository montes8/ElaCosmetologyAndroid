package com.example.elacosmetologyandroid.ui.register

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.extensions.isEmailValid
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.core.KoinComponent
import org.koin.core.inject


class RegisterViewModel : BaseViewModel(), KoinComponent {



    var enableButton = MutableLiveData(false)

    var loadingButton = MutableLiveData(false)


    private val authUseCase: AuthUseCase by inject()



    fun register(pass : EditCustomLayout){
        if (validateEmail(pass)){
            loadingButton.postValue(true)
            executeSuspend {

            }
        }
    }

    fun validateRegister(name : EditCustomLayout, lastName : EditCustomLayout,
                         email : EditCustomLayout, pass : EditCustomLayout){
        name.uiErrorMessage = EMPTY
        lastName.uiErrorMessage = EMPTY
        email.uiErrorMessage = EMPTY
        pass.uiErrorMessage = EMPTY
        enableButton.postValue(!TextUtils.isEmpty(email.uiText) && !TextUtils.isEmpty(pass.uiText )
                && !TextUtils.isEmpty(name.uiText )&& !TextUtils.isEmpty(lastName.uiText ) )
    }

    private fun validateEmail(pass : EditCustomLayout):Boolean{
        if (!isEmailValid(pass.uiText)){
            pass.uiErrorMessage = "El correo ingresado no es valido"
            return false
        }
        return true
    }

}