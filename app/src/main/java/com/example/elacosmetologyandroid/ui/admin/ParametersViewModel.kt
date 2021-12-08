package com.example.elacosmetologyandroid.ui.admin

import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.isNotEmpty
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.component.edit.EditCustomLayout
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.model.VideoModel
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.usecases.usecases.AppUseCase
import org.koin.core.KoinComponent
import org.koin.core.inject


class ParametersViewModel : BaseViewModel(), KoinComponent {

    val successParamDefaultLiveData        : LiveData<ParamModel> get()   = _successParamDefaultLiveData
    private val _successParamDefaultLiveData    = MutableLiveData<ParamModel>()

    val successUpdateParamLiveData        : LiveData<ParamModel> get()   = _successUpdateParamLiveData
    private val _successUpdateParamLiveData    = MutableLiveData<ParamModel>()

    val successSaveVideoLiveData        : LiveData<VideoModel> get()   = _successSaveVideoLiveData
    private val _successSaveVideoLiveData    = MutableLiveData<VideoModel>()

    val registerObserver = ObservableBoolean(true)
    private var paramModel : ParamModel = ParamModel()

    private var videoModel : VideoModel = VideoModel()

    private val appUseCase: AppUseCase by inject()


    fun loadParam(){
        executeSuspendNotProgress{
            val response = appUseCase.loadParam()
            if (response.id.isNotEmpty())configDefault(response)
            _successParamDefaultLiveData.postValue(response)
        }
    }

    fun saveParam(btnParam : ProgressButton){
        btnParam.isButtonLoading = true
        executeSuspendNotProgress {
            val response = appUseCase.saveParam(paramModel)
            configDefault(response)
            _successUpdateParamLiveData.postValue(response)
        }
    }

    fun saveVideo(btnVideo : ProgressButton){
        btnVideo.isButtonLoading = true
        executeSuspendNotProgress {
            val response = appUseCase.saveVideo(videoModel)
            _successSaveVideoLiveData.postValue(response)
        }
    }

    fun updateParam(btnParam : ProgressButton){
        btnParam.isButtonLoading = true
        executeSuspendNotProgress {
            val response = appUseCase.updateParam(paramModel)
            configDefault(response)
            _successUpdateParamLiveData.postValue(response)
        }
    }

    private fun configDefault(param : ParamModel){
        paramModel = param
        registerObserver.set(paramModel.enableRegister)
    }

    fun validateParam(title : EditCustomLayout,description : AppCompatEditText,btnParam : ProgressButton){
        btnParam.isButtonEnabled = title.uiText.isNotEmpty() && description.text.toString().isNotEmpty()
        paramModel.title = title.uiText
        paramModel.description = description.text.toString()
        paramModel.enableRegister = registerObserver.get()
    }


    fun validateVideo(idVideo : EditCustomLayout,author : EditCustomLayout,nameVideo : EditCustomLayout,description : AppCompatEditText,btnVideo : ProgressButton){
        btnVideo.isButtonEnabled = idVideo.uiText.isNotEmpty() && author.uiText.isNotEmpty() && nameVideo.uiText.isNotEmpty() && description.text.toString().isNotEmpty()
        videoModel.idMovie = idVideo.uiText
        videoModel.nameVideo = nameVideo.uiText
        videoModel.description = description.text.toString()
        videoModel.author = author.uiText
    }

}