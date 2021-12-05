package com.example.elacosmetologyandroid.ui.admin

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityParametersBinding
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel

class ParametersActivity : BaseActivity() {

    private lateinit var binding: ActivityParametersBinding

    companion object { fun start(context: Context) { context.startActivity(Intent(context, ParametersActivity::class.java)) } }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parameters)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
    }

    override fun observeViewModel() {}

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = true

}