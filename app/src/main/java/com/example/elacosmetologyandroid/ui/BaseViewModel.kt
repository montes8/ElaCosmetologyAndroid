package com.example.elacosmetologyandroid.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    val errorLiveData = MutableLiveData<Throwable>()
    val progressLiveData = MutableLiveData<Boolean>()



    fun executeSuspendNoProgress(func: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                func()
            } catch (ex: Exception) {
                errorLiveData.postValue(ex)
            }
        }

    fun executeSuspend(func: suspend () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                progressLiveData.postValue(true)
                func()
                progressLiveData.postValue(false)
            } catch (ex: Exception) {
                ex.printStackTrace()
                progressLiveData.postValue(false)
                errorLiveData.postValue(ex)
            }
        }

}