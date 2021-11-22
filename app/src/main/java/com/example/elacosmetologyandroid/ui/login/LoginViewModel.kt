package com.example.elacosmetologyandroid.ui.login

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


class LoginViewModel : BaseViewModel(), KoinComponent {


    val successLoginLiveData        : LiveData<User?> get()   = _successLoginLiveData
    private val _successLoginLiveData    = MutableLiveData<User?>()


    private val authUseCase: AuthUseCase by inject()



    fun login(email : EditCustomLayout,pass : EditCustomLayout,btnLogin : ProgressButton){
        if (validateEmail(email)){
            btnLogin.isButtonLoading = true
            executeSuspend {
                val response = authUseCase.login(email.uiText,pass.uiText)
                _successLoginLiveData.postValue(response)
            }
        }
    }


    fun validateLogin(email : EditCustomLayout, pass : EditCustomLayout,btnLogin : ProgressButton){
        pass.uiErrorMessage = EMPTY
        btnLogin.isButtonEnabled = email.uiText.isNotEmpty() && pass.uiText.isNotEmpty()
    }

    private fun validateEmail(email : EditCustomLayout):Boolean{
        if (!isEmailValid(email.uiText)){
            email.uiErrorMessage = "El correo ingresado no es valido"
            return false
        }
        return true
    }

}