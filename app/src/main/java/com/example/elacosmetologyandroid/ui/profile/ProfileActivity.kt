package com.example.elacosmetologyandroid.ui.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityProfileBinding
import com.example.elacosmetologyandroid.extensions.*
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.model.PlaceModel
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.address.AddressActivity
import com.example.elacosmetologyandroid.ui.login.LoginActivity
import com.example.elacosmetologyandroid.ui.login.LoginViewModel
import com.example.elacosmetologyandroid.utils.DATA_ADDRESS_PLACE
import com.example.elacosmetologyandroid.utils.NAME_PATH_PROFILE
import com.example.elacosmetologyandroid.utils.TYPE_USER
import com.example.elacosmetologyandroid.utils.TYPE_USER_BANNER
import com.example.elacosmetologyandroid.utils.controller.CameraController
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.nav_header_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import kotlin.math.abs

class ProfileActivity : BaseActivity(), CameraController.CameraControllerListener  {



    private val viewModel: ProfileViewModel by viewModel(clazz = ProfileViewModel::class)

    private lateinit var addressResult: ActivityResultLauncher<Int>
    private var cameraManager: CameraController? = null
    private var userModel : User? = null
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
        configResult()
        configAppBar()
        configChangeEdit()
        configDataUser()

    }

    private fun configDataUser(){
        UserTemporary.getUser()?.let {
            userModel = it
            binding.user = it
            binding.imgProfile.urlCustomImage(TYPE_USER,it.uid,false)
            binding.imgImageBanner.urlCustomImageBanner(TYPE_USER_BANNER,it.uid)
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

        viewModel.errorLiveData.observe(this,{
            hideLoadingImage()
            hieLoadingImageBanner()
            binding.btnSaveProfile.isButtonLoading = false
        })

        viewModel.successUpdateUserLiveData.observe(this,{
            it?.apply {
                configDataUser()
                successUserSnackBar()
                enableViewEdit(false)
            }
        })

        viewModel.successDeleteAndInactiveUserLiveData.observe(this,{
            LoginActivity.start(this)
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
        binding.btnSaveProfile.setOnClickButtonDelayListener{viewModel.updateProfile(binding.editEmailProfile,binding.btnSaveProfile)}
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
    }

    private fun onClickImage(value : Boolean){
        typeBanner = value
        cameraManager?.doCamera()
    }

    private fun popUpDeleteAndDeactivateUser(value: Boolean){
        showDialogCustom(R.layout.dialog_generic, true,imageVisibility = false,description =
        if (value)"Estas segura que deseas desactivar tu cuenta temporalmente" else "Estas segura de eliminar tu cuenta definitivamente todo tus datos se borran") {
           userModel?.let { if (value)viewModel.inactiveAccount(it.uid) else viewModel.deleteAccount(it.uid)}
        }
    }


    private fun validateData(){
        viewModel.validateProfile(binding.editNameProfile,binding.editLastNameProfile, binding.editEmailProfile,
            binding.editPhoneProfile,binding.editAddressProfile,binding.btnSaveProfile)
    }

    private fun configChangeEdit(){
        binding.editNameProfile.uiEditCustomListener = {validateData()}
        binding.editLastNameProfile.uiEditCustomListener = {validateData()}
        binding.editEmailProfile.uiEditCustomListener = {validateData()}
        binding.editPhoneProfile.uiEditCustomListener = {validateData()}
        binding.editAddressProfile.uiEditCustomListener = {validateData()}
        binding.editAddressProfile.uiEditClickListener = {addressResult.launch(null) }
    }

    private fun enableViewEdit(value : Boolean){
        configFocusableAndCursor(value)
        binding.btnSaveProfile.isButtonVisible = value
        binding.imgEditProfile.validateVisibility(!value)
        binding.txtDeactivateUser.validateVisibility(value)
        binding.txtDeleteUser.validateVisibility(value)
        if (value)genericSnackBar(true)
        flagClick = !flagClick
    }


    private fun configFocusableAndCursor(value : Boolean){
        binding.editNameProfile.uiFocusable = value
        binding.editLastNameProfile.uiFocusable = value
        binding.editEmailProfile.uiFocusable = value
        binding.editPhoneProfile.uiFocusable = value
        binding.editNameProfile.uiCursorVisibility = value
        binding.editLastNameProfile.uiCursorVisibility = value
        binding.editEmailProfile.uiCursorVisibility = value
        binding.editPhoneProfile.uiCursorVisibility = value
    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getValidActionToolBar() = false

    override fun onCameraPermissionDenied() {
        genericSnackBar(false)
    }

    private fun configResult() {
        addressResult = registerForActivityResult(StartActivityContract(AddressActivity.start(this))) {
            it?.let {
                val place: PlaceModel? = it.getParcelable(DATA_ADDRESS_PLACE)
                place?.let { item ->
                    binding.editAddressProfile.uiText = item.name
                }
            }
        }
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        if (typeBanner){ updateImageBanner(path,img) }else{ updateImageProfile(path,img) }
    }

    private fun updateImageBanner(path: String, img: Bitmap){
        showLoadingImageBanner()
        UserTemporary.getUser()?.let { viewModel.updateImageProfileBanner(it.uid, File(path)) }
        binding.imgImageBanner.setImageBitmap(img)
    }

    private fun updateImageProfile(path: String, img: Bitmap){
        showLoadingImage()
        UserTemporary.getUser()?.let { viewModel.updateImageProfile(it.uid, File(path)) }
        binding.imgProfile.setImageBitmap(img)
    }


    private fun successUserSnackBar(){
        showSnackBarCustom(binding.snackBarActivateProfile,"Sus datos de actualizarón correctamente")
    }


    private fun genericSnackBarSuccess(value: Boolean){
        showSnackBarCustom(binding.snackBarActivateProfile,message = if (value)getString(R.string.text_success_profile_photo)else
            getString(R.string.text_success_profile_banner__photo))
    }

    private fun genericSnackBarError(){
        showSnackBarCustom(binding.snackBarActivateProfile,getString(R.string.error_image_update),
        colorBg = R.color.red)
    }

    private fun genericSnackBar(value: Boolean){
        showSnackBarCustom(binding.snackBarActivateProfile,message = if (value)getString(R.string.text_message_profile_photo)else
            getString(R.string.error_denied_camera)
            ,colorBg = if (value)R.color.pink_200 else R.color.red)
    }

    private fun showLoadingImage(){
        binding.imgProfile.alpha= 0.2f
        binding.avLoadingProfile.visible()
        binding.avLoadingProfile.show()

    }

    private fun showLoadingImageBanner(){
        binding.imgImageBanner.alpha= 0.2f
        binding.avLoadingBanner.visible()
        binding.avLoadingBanner.show()

    }

    private fun hideLoadingImage(){
        binding.imgProfile.alpha= 1f
        binding.avLoadingProfile.gone()
        binding.avLoadingProfile.hide()
    }

    private fun hieLoadingImageBanner(){
        binding.imgImageBanner.alpha= 1f
        binding.avLoadingBanner.gone()
        binding.avLoadingBanner.hide()
    }
}