package com.example.elacosmetologyandroid.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.TYPE_USER
import com.example.elacosmetologyandroid.utils.TYPE_USER_BANNER
import com.example.elacosmetologyandroid.utils.validateEmail
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File


class ProfileViewModel : BaseViewModel(), KoinComponent {

    private val authUseCase: AuthUseCase by inject()

    val successImageLiveData        : LiveData<Boolean> get()   = _successImageLiveData
    private val _successImageLiveData    = MutableLiveData<Boolean>()

    val successImageBannerLiveData        : LiveData<Boolean> get()   = _successImageBannerLiveData
    private val _successImageBannerLiveData    = MutableLiveData<Boolean>()

    val successUpdateUserLiveData        : LiveData<User> get()   = _successUpdateUserLiveData
    private val _successUpdateUserLiveData    = MutableLiveData<User>()

    val successDeleteAndInactiveUserLiveData        : LiveData<User> get()   = _successDeleteAndInactiveUserLiveData
    private val _successDeleteAndInactiveUserLiveData    = MutableLiveData<User>()


    var userData: User? = null


    fun setUserConfig(value : User){
        userData = value
    }


    fun updateImageProfile(idUser:String,file :File){
        executeSuspendNotError {
            val response = authUseCase.updateImage(TYPE_USER,idUser,file)
            _successImageLiveData.postValue(response)
        }

    }

    fun updateImageProfileBanner(idUser:String,file :File){
        executeSuspendNotError {
            val response = authUseCase.updateImageBanner(TYPE_USER_BANNER,idUser,file)
            _successImageBannerLiveData.postValue(response)
        }
    }

    fun deleteAccount(idUser: String) {
                executeSuspend {
                    val response = authUseCase.deleteAccount(idUser)
                    _successDeleteAndInactiveUserLiveData.postValue(response)
                }

    }

    fun inactiveAccount(idUser: String) {
                executeSuspend {
                    val response = authUseCase.inactiveAccount(idUser)
                    _successDeleteAndInactiveUserLiveData.postValue(response)
                }

    }

    fun updateProfile(pass: EditCustomLayout, btnProgress: ProgressButton) {
        if (validateEmail(pass)) {
            userData?.let {
                btnProgress.isButtonLoading = true
                executeSuspendNotProgress {
                    val response = authUseCase.updateUser(it)
                    setUserConfig(response)
                    _successUpdateUserLiveData.postValue(response)
                }
            }
        }
    }


    fun validateProfile(
        name: EditCustomLayout,
        lastName: EditCustomLayout,
        email: EditCustomLayout,
        phone: EditCustomLayout,
        address: EditCustomLayout,
        btnProgress: ProgressButton
    ) {
        name.uiErrorMessage = EMPTY
        lastName.uiErrorMessage = EMPTY
        email.uiErrorMessage = EMPTY
        btnProgress.isButtonEnabled = name.uiText.isNotEmpty() && lastName.uiText.isNotEmpty()
                && email.uiText.isNotEmpty() && phone.uiText.isNotEmpty() && address.uiText.isNotEmpty()


        updateUser(name, lastName, email, phone, address)
    }

    private fun updateUser(
        name: EditCustomLayout, lastName: EditCustomLayout, email: EditCustomLayout, phone: EditCustomLayout, address: EditCustomLayout
    ) {
        userData?.name = name.uiText
        userData?.lastName = lastName.uiText
        userData?.email = email.uiText
        userData?.phone = phone.uiText
        userData?.address = address.uiText

    }

}