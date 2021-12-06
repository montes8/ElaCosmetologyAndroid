package com.example.elacosmetologyandroid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.usecases.usecases.AppUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class AppViewModel : BaseViewModel(), KoinComponent {


    val successSessionLiveData        : LiveData<Boolean> get()   = _successSessionLiveData
    private val _successSessionLiveData    = MutableLiveData<Boolean>()

    val successBannerLiveData        : LiveData<List<ModelGeneric>> get()   = _successBannerLiveData
    private val _successBannerLiveData    = MutableLiveData<List<ModelGeneric>>()

    private val appUseCase: AppUseCase by inject()

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

    fun logout(){
        executeSuspendNotProgress { appUseCase.logout() }
    }

}