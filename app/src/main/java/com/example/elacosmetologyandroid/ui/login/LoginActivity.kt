package com.example.elacosmetologyandroid.ui.login

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityLoginBinding
import com.example.elacosmetologyandroid.extensions.setColouredSpanClick
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity() {

    private val loginViewModel: LoginViewModel by viewModel(clazz = LoginViewModel::class)
    private lateinit var binding: ActivityLoginBinding

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, LoginActivity::class.java)) }
    }


    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.loginViewModel = loginViewModel
        binding.lifecycleOwner = this

    }

    override fun setUpView() {
        configEditChangeAndAction()
        configRegister()
    }

    private fun configEditChangeAndAction(){
        binding.editEmail.uiEditCustomListener = {loginViewModel.validateLogin(binding.editEmail,binding.editPassword)}
        binding.editPassword.uiEditCustomListener = {loginViewModel.validateLogin(binding.editEmail,binding.editPassword)}
        binding.btnLogin.setOnClickButtonDelayListener{loginViewModel.login(binding.editPassword)}
    }

    private fun configRegister(){
        binding.txtRegister.setColouredSpanClick(getString(R.string.text_register), ContextCompat.getColor(
            this, R.color.pink_600),true) { RegisterActivity.start(this) }
    }

    override fun observeLiveData() {
    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()


}