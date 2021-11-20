package com.example.elacosmetologyandroid.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityLoginBinding
import com.example.elacosmetologyandroid.ui.BaseActivity

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, LoginActivity::class.java)) }
    }


    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
    }

    override fun observeLiveData() {
    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()


}