package com.example.elacosmetologyandroid.ui.admin.param

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.elacosmetologyandroid.databinding.FragmentParamBinding
import com.example.elacosmetologyandroid.model.ParamModel
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ParamFragment : BaseFragment() {

    private val viewModel: ParamViewModel by viewModel(clazz = ParamViewModel::class)

    private lateinit var binding: FragmentParamBinding

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
        viewModel.loadParam()
        configAction()
    }

    override fun setBundle() {}

    override fun observeLiveData() {
        viewModel.successListParamLiveData.observe(this, {
            it?.apply {
                if (this.isNotEmpty())configDataParam(this[0])
            }
        })

        viewModel.successParamLiveData.observe(this,{
            binding.btnSaveParam.isButtonLoading = false
        })

        viewModel.errorLiveData.observe(this,{
            binding.btnSaveParam.isButtonLoading = false
        })
    }

    private fun configAction(){
        binding.btnSaveParam.setOnClickButtonDelayListener{
          viewModel.saveParam(binding.btnSaveParam)
        }

        binding.editTitleParam.uiEditCustomListener = {viewModel.validateParam(binding.editTitleParam,
            binding.editDescriptionParam,binding.btnSaveParam)}

        binding.editDescriptionParam.addTextChangedListener { viewModel.validateParam(binding.editTitleParam,
            binding.editDescriptionParam,binding.btnSaveParam) }
    }

    private fun configDataParam(paramData : ParamModel){
        binding.param = paramData
        binding.btnSaveParam.isButtonEnabled = true
    }

    override fun getViewModel(): BaseViewModel = viewModel
}