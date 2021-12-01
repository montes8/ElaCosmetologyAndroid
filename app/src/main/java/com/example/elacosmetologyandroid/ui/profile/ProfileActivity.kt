package com.example.elacosmetologyandroid.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityProfileBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.extensions.validateVisibility
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ProfileActivity : BaseActivity() {


    private var flagClick = true

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, ProfileActivity::class.java)) }
    }

    private lateinit var binding: ActivityProfileBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configAppBar()

    }

    override fun observeViewModel() {

    }

    private fun configAppBar(){
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        setSupportActionBar(binding.toolbar)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
           binding.imgProfile.validateVisibility(abs(verticalOffset) - appBarLayout.totalScrollRange != 0)
           if (flagClick) binding.imgEditProfile.validateVisibility(abs(verticalOffset) - appBarLayout.totalScrollRange != 0)
        })
        initOnClick()
    }

    private fun initOnClick(){
        binding.imgEditProfile.setOnClickDelay {
            enableViewEdit(true)
            flagClick = false
        }
        binding.imgBannerProfile.setOnClickDelay {}
        binding.imgProfile.setOnClickDelay {}
        binding.btnSaveProfile.setOnClickButtonDelayListener{enableViewEdit(false)
            flagClick = true}
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun enableViewEdit(value : Boolean){
        binding.editNameProfile.uiFocusable = value
        binding.editLastNameProfile.uiFocusable = value
        binding.editEmailProfile.uiFocusable = value
        binding.editPhoneProfile.uiFocusable = value
        binding.editAddressProfile.uiFocusable = value
        binding.editNameProfile.uiCursorVisibility = value
        binding.editLastNameProfile.uiCursorVisibility = value
        binding.editEmailProfile.uiCursorVisibility = value
        binding.editPhoneProfile.uiCursorVisibility = value
        binding.editAddressProfile.uiCursorVisibility = value
        binding.btnSaveProfile.isButtonVisible = value
        binding.imgEditProfile.validateVisibility(!value)

    }

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = false
}