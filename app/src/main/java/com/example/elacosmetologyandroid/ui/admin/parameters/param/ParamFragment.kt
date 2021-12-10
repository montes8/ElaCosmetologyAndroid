package com.example.elacosmetologyandroid.ui.admin.parameters.banner.param

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.elacosmetologyandroid.databinding.FragmentParamBinding
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.admin.parameters.ParametersActivity
import com.example.elacosmetologyandroid.ui.admin.parameters.banner.ParametersViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParamFragment : BaseFragment() {

    private val viewModel: ParametersViewModel by viewModel(clazz = ParametersViewModel::class)
    private lateinit var binding: FragmentParamBinding
    private var flagUpdateParam = false

    companion object {
        fun newInstance() = ParamFragment()
    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentParamBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.param = ParamModel()
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        configAction()
    }

    override fun setBundle() {
        viewModel.loadParam()
    }

    override fun observeLiveData() {
        viewModel.successParamDefaultLiveData.observe(this, { it?.apply {
            binding.nsvParam.validateVisibility(true,binding.shimmerParam)
            if (this.id.isNotEmpty()){ configDataParam(this) } }
        })

        viewModel.successUpdateParamLiveData.observe(this,{
            it?.apply { binding.param = this }
            binding.btnSaveParam.isButtonLoading = false
            (activity as ParametersActivity).showSuccessDialog()
        })

        viewModel.errorLiveData.observe(this,{
            binding.nsvParam.validateVisibility(true,binding.shimmerParam)
            (activity as ParametersActivity).errorSnackBarSaveData()
            binding.btnSaveParam.isButtonLoading = false })
    }

    private fun configAction(){
        binding.btnSaveParam.setOnClickButtonDelayListener{
          if (flagUpdateParam)viewModel.updateParam(binding.btnSaveParam) else viewModel.saveParam(binding.btnSaveParam)
        }

        binding.editTitleParam.uiEditCustomListener = {viewModel.validateParam(binding.editTitleParam,
            binding.editDescriptionParam,binding.btnSaveParam)}

        binding.editDescriptionParam.addTextChangedListener { viewModel.validateParam(binding.editTitleParam,
            binding.editDescriptionParam,binding.btnSaveParam) }
    }

    private fun configDataParam(paramData : ParamModel){
        flagUpdateParam = true
        binding.param = paramData
        binding.btnSaveParam.isButtonEnabled = true
    }

    override fun getViewModel(): BaseViewModel = viewModel
}