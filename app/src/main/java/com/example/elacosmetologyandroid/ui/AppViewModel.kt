package com.example.elacosmetologyandroid.ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.usecases.usecases.AppUseCase
import com.example.elacosmetologyandroid.usecases.usecases.AuthUseCase
import com.example.elacosmetologyandroid.utils.TYPE_USER
import org.koin.core.KoinComponent
import org.koin.core.inject


class AppViewModel : BaseViewModel(), KoinComponent {


    val successSessionLiveData        : LiveData<Boolean> get()   = _successSessionLiveData
    private val _successSessionLiveData    = MutableLiveData<Boolean>()

    val successBannerLiveData        : LiveData<List<ModelGeneric>> get()   = _successBannerLiveData
    private val _successBannerLiveData    = MutableLiveData<List<ModelGeneric>>()

    val successImageLiveData        : LiveData<Bitmap> get()   = _successImageLiveData
    private val _successImageLiveData    = MutableLiveData<Bitmap>()



    private val appUseCase: AppUseCase by inject()
    private val authUseCase: AuthUseCase by inject()

    fun session(){
            executeSuspendNotProgress {
                val response = appUseCase.session()
                _successSessionLiveData.postValue(response)
            }
    }

    fun loadBanner(){
        executeSuspendNotProgress {
            val response = appUseCase.loadBanner()
            _successBannerLiveData.postValue(response)
        }
    }

    fun loadImage(type:String,idUser : String){
        executeSuspendNotProgress {
            val response = authUseCase.loadImage(type,idUser)
            _successImageLiveData.postValue(response)
        }
    }

    fun logout(){
        executeSuspendNotProgress { appUseCase.logout() }
    }

}