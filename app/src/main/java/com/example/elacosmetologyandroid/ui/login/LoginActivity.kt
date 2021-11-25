package com.example.elacosmetologyandroid.ui.login

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityLoginBinding
import com.example.elacosmetologyandroid.extensions.setColouredSpanClick
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.home.HomeActivity
import com.example.elacosmetologyandroid.ui.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModel(clazz = LoginViewModel::class)
    private lateinit var binding: ActivityLoginBinding

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }


    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configEditChangeAndAction()
        configRegister()
    }

    private fun configEditChangeAndAction(){
        binding.editEmail.uiEditCustomListener = {validateData()}
        binding.editPassword.uiEditCustomListener = {validateData()}
        binding.btnLogin.setOnClickButtonDelayListener{login()}
    }

    private fun configRegister(){
        binding.txtRegister.setColouredSpanClick(getString(R.string.text_register), ContextCompat.getColor(
            this, R.color.pink_600),true) { RegisterActivity.start(this) }
    }

    override fun observeViewModel() {
        viewModel.errorLiveData.observe(this,{
            binding.btnLogin.isButtonLoading = false
        })

        viewModel.successLoginLiveData.observe(this,{
            it?.apply {
                HomeActivity.start(this@LoginActivity)
            }
        })
    }

    private fun login(){
        viewModel.login(binding.editEmail,binding.editPassword,binding.btnLogin)
    }

    private fun validateData(){
        viewModel.validateLogin(binding.editEmail,binding.editPassword,binding.btnLogin)
    }

    override fun getValidActionToolBar() = false

    override fun getViewModel(): BaseViewModel = viewModel


}