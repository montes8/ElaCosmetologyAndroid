package com.example.elacosmetologyandroid.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.validateEmail
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File


class RegisterViewModel : BaseViewModel(), KoinComponent {

    private val authUseCase: AuthUseCase by inject()

    val successAccountLiveData: LiveData<User?> get() = _successAccountLiveData
    private val _successAccountLiveData = MutableLiveData<User?>()

    val successImageLiveData: LiveData<String> get() = _successImageLiveData
    private val _successImageLiveData = MutableLiveData<String>()


    var userData: User? = null


    fun register(pass: EditCustomLayout, btnProgress: ProgressButton,file: File?) {
        if (validateEmail(pass)) {
            userData?.let {
                btnProgress.isButtonLoading = true
                executeSuspendNotProgress {
                    val response = authUseCase.register(it,file)
                    _successAccountLiveData.postValue(response)
                }
            }
        }
    }


    fun validateRegister(
        name: EditCustomLayout,
        lastName: EditCustomLayout,
        email: EditCustomLayout,
        pass: EditCustomLayout,
        phone: EditCustomLayout,
        address: EditCustomLayout,
        btnProgress: ProgressButton
    ) {
        name.uiErrorMessage = EMPTY
        lastName.uiErrorMessage = EMPTY
        email.uiErrorMessage = EMPTY
        pass.uiErrorMessage = EMPTY
        btnProgress.isButtonEnabled = name.uiText.isNotEmpty() && lastName.uiText.isNotEmpty()
                && email.uiText.isNotEmpty() && pass.uiText.isNotEmpty() && phone.uiText.isNotEmpty() && address.uiText.isNotEmpty()


        updateUser(name, lastName, email, pass, phone, address)
    }

    private fun updateUser(
        name: EditCustomLayout, lastName: EditCustomLayout, email: EditCustomLayout,
        pass: EditCustomLayout, phone: EditCustomLayout, address: EditCustomLayout
    ) {
        userData = User(
            name = name.uiText,
            lastName = lastName.uiText,
            email = email.uiText,
            password = pass.uiText,
            phone = phone.uiText,
            address = address.uiText
        )
    }


}