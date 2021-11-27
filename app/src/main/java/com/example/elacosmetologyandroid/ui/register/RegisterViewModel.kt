package com.example.elacosmetologyandroid.ui.register

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.extensions.isEmailValid
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.core.KoinComponent
import org.koin.core.inject


class RegisterViewModel : BaseViewModel(), KoinComponent {

    private val authUseCase: AuthUseCase by inject()
    private val context: Context by inject()

    val successAccountLiveData        : LiveData<User?> get()   = _successAccountLiveData
    private val _successAccountLiveData    = MutableLiveData<User?>()


    var userData : User? = null


    fun register(pass : EditCustomLayout,btnProgress : ProgressButton){
        if (validateEmail(pass)){
            userData?.let {
                btnProgress.isButtonLoading = true
                 executeSuspendNotProgress {
                    val response = authUseCase.register(it)
                    _successAccountLiveData.postValue(response)
                }
            }
        }
    }

    fun validateRegister(name : EditCustomLayout, lastName : EditCustomLayout,
                         email : EditCustomLayout, pass : EditCustomLayout,btnProgress : ProgressButton){
        name.uiErrorMessage = EMPTY
        lastName.uiErrorMessage = EMPTY
        email.uiErrorMessage = EMPTY
        pass.uiErrorMessage = EMPTY
        btnProgress.isButtonEnabled = name.uiText.isNotEmpty() && lastName.uiText.isNotEmpty()
                && email.uiText.isNotEmpty() && pass.uiText.isNotEmpty()
                userData = User(name = "${name.uiText} ${lastName.uiText}",email = email.uiText,password = pass.uiText)
    }

    private fun validateEmail(email : EditCustomLayout):Boolean{
        if (!isEmailValid(email.uiText)){
            email.uiErrorMessage = context.getString(R.string.error_email_format)
            return false
        }
        return true
    }

}