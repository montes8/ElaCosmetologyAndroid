package com.example.elacosmetologyandroid.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityProfileBinding
import com.example.elacosmetologyandroid.extensions.*
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.login.LoginViewModel
import com.example.elacosmetologyandroid.utils.NAME_PATH_PROFILE
import com.example.elacosmetologyandroid.utils.TYPE_USER
import com.example.elacosmetologyandroid.utils.controller.CameraController
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.nav_header_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import kotlin.math.abs

class ProfileActivity : BaseActivity(), CameraController.CameraControllerListener  {



    private val viewModel: ProfileViewModel by viewModel(clazz = ProfileViewModel::class)

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
        configDataUser()

    }

    private fun configDataUser(){
        UserTemporary.getUser()?.let {
            binding.user = it
            viewModel.loadImageProfile(TYPE_USER,it.uid)
            viewModel.loadImageProfileBanner(TYPE_USER,it.uid)
        }
    }

    override fun observeViewModel() {
        viewModel.successImageLiveData.observe(this,{
            hideLoadingImage()
            binding.imgProfile.validatePairVisibility(true,binding.avLoadingProfile)
            if (it)genericSnackBarSuccess(true) else genericSnackBarError()
        })
        viewModel.successImageBannerLiveData.observe(this,{
            hieLoadingImageBanner()
            binding.imgImageBanner.validatePairVisibility(true,binding.avLoadingBanner)
            if (it)genericSnackBarSuccess(false) else genericSnackBarError()
        })

        viewModel.successImageBitmapBannerLiveData.observe(this,{ it?.apply {   binding.imgImageBanner.setImageBitmap(this) } })

        viewModel.successImageBitmapLiveData.observe(this,{ it?.apply {    binding.imgProfile.setImageBitmap(this)} })

        viewModel.errorLiveData.observe(this,{
            hideLoadingImage()
            hieLoadingImageBanner()
        })

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
        configFocusableAndCursor(value)
        configEnableEdit(value)
        binding.btnSaveProfile.isButtonVisible = value
        binding.imgEditProfile.validateVisibility(!value)
        binding.txtDeactivateUser.validateVisibility(value)
        binding.txtDeleteUser.validateVisibility(value)
        if (value)genericSnackBar(true)
        flagClick = !flagClick
    }

    private fun configEnableEdit(value : Boolean){
        binding.editNameProfile.uiEnable = value
        binding.editLastNameProfile.uiEnable = value
        binding.editEmailProfile.uiEnable = value
        binding.editPhoneProfile.uiEnable = value
        binding.editAddressProfile.uiEnable = value
    }

    private fun configFocusableAndCursor(value : Boolean){
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
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getValidActionToolBar() = false

    override fun onCameraPermissionDenied() {
        genericSnackBar(false)
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        if (typeBanner){ updateImageBanner(path,img) }else{ updateImageProfile(path,img) }
    }

    private fun updateImageBanner(path: String, img: Bitmap){
        binding.imgImageBanner.validatePairVisibility(false,binding.avLoadingBanner)
        UserTemporary.getUser()?.let { viewModel.updateImageProfileBanner(it.uid, File(path)) }
        binding.imgImageBanner.setImageBitmap(img)
    }

    private fun updateImageProfile(path: String, img: Bitmap){
        binding.imgProfile.validatePairVisibility(true,binding.avLoadingProfile)
        UserTemporary.getUser()?.let { viewModel.updateImageProfile(it.uid, File(path)) }
        binding.imgProfile.setImageBitmap(img)
    }

    private fun genericSnackBarSuccess(value: Boolean){
        showSnackBarCustom(binding.snackBarActivateProfile,if (value)getString(R.string.text_success_profile_photo)else
            getString(R.string.text_success_profile_banner__photo))
    }

    private fun genericSnackBarError(){
        showSnackBarCustom(binding.snackBarActivateProfile,getString(R.string.error_image_update),
        R.color.red)
    }

    private fun genericSnackBar(value: Boolean){
        showSnackBarCustom(binding.snackBarActivateProfile,if (value)getString(R.string.text_message_profile_photo)else
            getString(R.string.error_denied_camera)
            ,colorBg = if (value)R.color.pink_200 else R.color.red)
    }

    private fun showLoadingImage(){
        binding.avLoadingProfile.visible()
        binding.avLoadingProfile.show()

    }

    private fun showLoadingImageBanner(){
        binding.avLoadingBanner.visible()
        binding.avLoadingBanner.show()

    }

    private fun hideLoadingImage(){
        binding.avLoadingProfile.gone()
        binding.avLoadingProfile.hide()
    }

    private fun hieLoadingImageBanner(){
        binding.avLoadingBanner.gone()
        binding.avLoadingBanner.hide()
    }
}