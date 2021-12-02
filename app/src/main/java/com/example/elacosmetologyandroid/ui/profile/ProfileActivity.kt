package com.example.elacosmetologyandroid.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityProfileBinding
import com.example.elacosmetologyandroid.extensions.setOnClickDelay
import com.example.elacosmetologyandroid.extensions.showDialogCustom
import com.example.elacosmetologyandroid.extensions.showSnackBarCustom
import com.example.elacosmetologyandroid.extensions.validateVisibility
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.utils.NAME_PATH_PROFILE
import com.example.elacosmetologyandroid.utils.controller.CameraController
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class ProfileActivity : BaseActivity(), CameraController.CameraControllerListener  {


    private var cameraManager: CameraController? = null
    private var flagClick = true
    private var typeBanner = true

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, ProfileActivity::class.java)) }
    }

    private lateinit var binding: ActivityProfileBinding

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        cameraManager = CameraController(this, NAME_PATH_PROFILE, this)
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
        configAction()
    }

    private fun configAction(){
        binding.imgEditProfile.setOnClickDelay { enableViewEdit(true) }
        binding.imgBannerProfile.setOnClickDelay {onClickImage(true)}
        binding.imgProfile.setOnClickDelay {onClickImage(false)}
        binding.txtDeleteUser.setOnClickDelay{popUpDeleteAndDeactivateUser(true)}
        binding.txtDeactivateUser.setOnClickDelay{popUpDeleteAndDeactivateUser(false)}
        binding.btnSaveProfile.setOnClickButtonDelayListener{enableViewEdit(false)}
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onClickImage(value : Boolean){
        typeBanner = value
        cameraManager?.doCamera()
    }

    private fun popUpDeleteAndDeactivateUser(value: Boolean){
        showDialogCustom(R.layout.dialog_generic, true,imageVisibility = false) {

        }
    }

    private fun popUpSuccessAction(){
        showDialogCustom(R.layout.dialog_generic, true,imageVisibility = false) {
        }
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
        binding.txtDeactivateUser.validateVisibility(value)
        binding.txtDeleteUser.validateVisibility(value)
        if (value)genericSnackBar(true)
        flagClick = !flagClick
    }

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = false

    override fun onCameraPermissionDenied() {
        genericSnackBar(false)
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        if (typeBanner){
            binding.imgBannerProfile.setImageBitmap(img)
        }else{
            binding.imgProfile.setImageBitmap(img)
        }
    }

    private fun genericSnackBar(value: Boolean){
        showSnackBarCustom(binding.snackBarActivateProfile,if (value)getString(R.string.text_message_profile_photo)else
            getString(R.string.error_denied_camera)
            ,colorBg = if (value)R.color.pink_200 else R.color.red)
    }
}