package com.example.elacosmetologyandroid.ui.admin.param

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.elacosmetologyandroid.databinding.FragmentParamBinding
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParamFragment : BaseFragment() {

    private val viewModel: ParamViewModel by viewModel(clazz = ParamViewModel::class)

    private lateinit var binding: FragmentParamBinding
    private var flagRegister = false

    companion object {
        fun newInstance() = ParamFragment()
    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentParamBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun setUpView() {
        viewModel.loadParam()
    }

    override fun setBundle() {}

    override fun observeLiveData() {
        viewModel.successListParamLiveData.observe(this, {
            it?.apply {
                if (this.isNotEmpty())configDataParam(this[0])
            }
        })
    }

    private fun configAction(){
        binding.btnSaveParam.setOnClickButtonDelayListener{

        }
    }

    private fun configDataParam(paramData : ParamModel){
        binding.param = paramData
        binding.btnSaveParam.isButtonEnabled = true
    }

    override fun getViewModel(): BaseViewModel? = null
}