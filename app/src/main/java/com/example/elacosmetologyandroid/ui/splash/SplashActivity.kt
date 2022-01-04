package com.example.elacosmetologyandroid.ui.splash


import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.databinding.ActivitySplashBinding
import com.example.elacosmetologyandroid.ui.AppViewModel
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.home.HomeActivity
import com.example.elacosmetologyandroid.ui.login.LoginActivity
import com.example.elacosmetologyandroid.ui.login.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    private val viewModel: AppViewModel by viewModel(clazz = AppViewModel::class)
    private lateinit var binding: ActivitySplashBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {}

    override fun onResume() {
        super.onResume()
        configAnimation()
    }

    private fun configAnimation(){
        val ani = AnimationUtils.loadAnimation(this, R.anim.animation_top)
        val ani2 = AnimationUtils.loadAnimation(this, R.anim.animation_botton)
        binding.ctlTop.animation=ani
        binding.ctlBottom.animation=ani2
        Handler(Looper.getMainLooper()).postDelayed({   viewModel.session()}, 2000)
    }

    override fun observeViewModel() {
        viewModel.successSessionLiveData.observe(this,{
            it?.apply { if (this) HomeActivity.start(this@SplashActivity) else LoginActivity.start(this@SplashActivity) }
        })

        viewModel.errorLiveData.observe(this,{
            LoginActivity.start(this@SplashActivity)
        })
    }

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = false

}