package com.example.elacosmetologyandroid.ui.register

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityRegisterBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : BaseActivity() {

    private val viewModel: RegisterViewModel by viewModel(clazz = RegisterViewModel::class)
    private lateinit var binding: ActivityRegisterBinding

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, RegisterActivity::class.java)) }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        binding.registerToolbar.txtTitleToolbar.text =  getString(R.string.text_register)
        configAction()
    }

    private fun configAction(){
        binding.editNameRegister.uiEditCustomListener={validateData()}
        binding.editLastNameRegister.uiEditCustomListener={validateData()}
        binding.editEmailRegister.uiEditCustomListener={validateData()}
        binding.editPasswordRegister.uiEditCustomListener={validateData()}
        binding.btnRegister.setOnClickButtonDelayListener{viewModel.register(binding.editEmailRegister,binding.btnRegister)}
    }

    private fun validateData(){
        viewModel.validateRegister(binding.editNameRegister,binding.editLastNameRegister,
            binding.editEmailRegister,binding.editPasswordRegister,binding.btnRegister)
    }

    override fun observeViewModel() {
        viewModel.errorLiveData.observe(this,{
            binding.btnRegister.isButtonLoading = false
        })

        viewModel.successAccountLiveData.observe(this,{
             it?.apply {
                 HomeActivity.start(this@RegisterActivity)

             }
        })
    }

    override fun getValidActionToolBar() = true

    override fun getViewModel(): BaseViewModel = viewModel

}