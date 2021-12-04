package com.example.elacosmetologyandroid.ui.profile

import android.content.Context
import android.graphics.Bitmap
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
import com.example.elacosmetologyandroid.utils.TYPE_USER
import com.example.elacosmetologyandroid.utils.TYPE_USER_BANNER
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.File


class ProfileViewModel : BaseViewModel(), KoinComponent {

    private val authUseCase: AuthUseCase by inject()

    val successImageLiveData        : LiveData<Boolean> get()   = _successImageLiveData
    private val _successImageLiveData    = MutableLiveData<Boolean>()

    val successImageBannerLiveData        : LiveData<Boolean> get()   = _successImageBannerLiveData
    private val _successImageBannerLiveData    = MutableLiveData<Boolean>()

    val successImageBitmapLiveData        : LiveData<Bitmap> get()   = _successImageBitmapLiveData
    private val _successImageBitmapLiveData    = MutableLiveData<Bitmap>()

    val successImageBitmapBannerLiveData        : LiveData<Bitmap> get()   = _successImageBitmapBannerLiveData
    private val _successImageBitmapBannerLiveData    = MutableLiveData<Bitmap>()


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

    fun loadImageProfile(type:String,idUser : String){
        executeSuspendNotProgress {
            val response = authUseCase.loadImage(type,idUser)
            _successImageBitmapLiveData.postValue(response)
        }
    }

    fun loadImageProfileBanner(type:String,idUser : String){
        executeSuspendNotProgress {
            val response = authUseCase.loadImage(type,idUser)
            _successImageBitmapBannerLiveData.postValue(response)
        }
    }


}