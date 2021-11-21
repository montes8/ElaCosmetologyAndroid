package com.example.elacosmetologyandroid.ui.splash


import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.databinding.ActivitySplashBinding
import com.example.elacosmetologyandroid.ui.login.LoginActivity

class SplashActivity : BaseActivity() {


    private lateinit var binding: ActivitySplashBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configAnimation()
    }

    private fun configAnimation(){
        val ani = AnimationUtils.loadAnimation(this, R.anim.animation_top)
        val ani2 = AnimationUtils.loadAnimation(this, R.anim.animation_botton)
        binding.ctlTop.animation=ani
        binding.ctlBottom.animation=ani2
        initSplash()
    }

    private fun initSplash(){
        Handler(Looper.getMainLooper()).postDelayed({
            LoginActivity.start(this)
        }, 4500)
    }

    override fun observeLiveData() {}

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()

}