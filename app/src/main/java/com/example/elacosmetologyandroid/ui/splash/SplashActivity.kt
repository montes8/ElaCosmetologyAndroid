package com.example.elacosmetologyandroid.ui.splash


import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {


    private lateinit var binding: ActivitySplashBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
    }



    override fun setUpView() {

    }

    override fun observeLiveData() {

    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()

}