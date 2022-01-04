package com.example.elacosmetologyandroid.ui.login

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityLoginBinding
import com.example.elacosmetologyandroid.extensions.setColouredSpanClick
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.extensions.turnOnScreen
import com.example.elacosmetologyandroid.extensions.validateVisibility
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.home.HomeActivity
import com.example.elacosmetologyandroid.ui.register.RegisterActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


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
        binding.ctlRegister.validateVisibility(UserTemporary.paramDefault.enableRegister)
        turnOnScreen(this)
    }

    private fun configEditChangeAndAction(){
        binding.editEmail.uiEditCustomListener = {validateData()}
        binding.editPassword.uiEditCustomListener = {validateData()}
        binding.btnLogin.setOnClickButtonDelayListener{ login()}
        binding.txtForgetPassword.setOnClickDelay{}
    }

    private fun configRegister(){
        binding.txtRegister.setColouredSpanClick(
            getString(R.string.text_register), ContextCompat.getColor(
                this, R.color.pink_600
            ), true
        ) { RegisterActivity.start(this) }
    }

    override fun observeViewModel() {
        viewModel.errorLiveData.observe(this, {
            binding.btnLogin.isButtonLoading = false
        })

        viewModel.successLoginLiveData.observe(this, {
            it?.apply {
                HomeActivity.start(this@LoginActivity)
            }
        })
    }

    private fun login(){
        viewModel.login(binding.editEmail, binding.editPassword, binding.btnLogin)
    }

    private fun validateData(){
        viewModel.validateLogin(binding.editEmail, binding.editPassword, binding.btnLogin)
    }

    override fun getValidActionToolBar() = false

    override fun getViewModel(): BaseViewModel = viewModel

}