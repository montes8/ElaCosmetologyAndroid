package com.example.elacosmetologyandroid.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityProfileBinding
import com.example.elacosmetologyandroid.extensions.gone
import com.example.elacosmetologyandroid.extensions.visible
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ProfileActivity : BaseActivity() {

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, ProfileActivity::class.java)) }
    }

    private lateinit var binding: ActivityProfileBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            if (abs(verticalOffset) -appBarLayout.totalScrollRange == 0)
            {
                //  Collapsed
                binding.cImgProfile.gone()

            }
            else
            {
                //Expanded
                binding.cImgProfile.visible()


            }

        })
    }

    override fun observeViewModel() {

    }

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = false
}