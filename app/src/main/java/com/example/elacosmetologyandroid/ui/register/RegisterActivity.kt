package com.example.elacosmetologyandroid.ui.register

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.activity.result.ActivityResultLauncher
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityRegisterBinding
import com.example.elacosmetologyandroid.extensions.*
import com.example.elacosmetologyandroid.extensions.showSnackBarCustom
import com.example.elacosmetologyandroid.model.PlaceModel
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.address.AddressActivity
import com.example.elacosmetologyandroid.ui.home.HomeActivity
import com.example.elacosmetologyandroid.utils.DATA_ADDRESS_PLACE
import com.example.elacosmetologyandroid.utils.NAME_PATH_PROFILE
import com.example.elacosmetologyandroid.utils.controller.CameraController
import kotlinx.android.synthetic.main.activity_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File


class RegisterActivity : BaseActivity() , CameraController.CameraControllerListener {

    private lateinit var addressResult: ActivityResultLauncher<Int>

    private val viewModel: RegisterViewModel by viewModel(clazz = RegisterViewModel::class)
    private lateinit var binding: ActivityRegisterBinding
    private var cameraManager: CameraController? = null
    private var file: File? = null

    companion object {
        fun start(context: Context) { context.startActivity(Intent(context, RegisterActivity::class.java)) }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configInit()
        configResult()
        configAction()
    }

    private fun configInit(){
        cameraManager = CameraController(this, NAME_PATH_PROFILE,  this)
        binding.registerToolbar.txtTitleToolbar.text =  getString(R.string.text_register)
    }

    private fun configAction(){
        binding.editNameRegister.uiEditCustomListener={validateData()}
        binding.editLastNameRegister.uiEditCustomListener={validateData()}
        binding.editEmailRegister.uiEditCustomListener={validateData()}
        binding.editPasswordRegister.uiEditCustomListener={validateData()}
        binding.editPhonedRegister.uiEditCustomListener={validateData()}
        binding.editAddressRegister.uiEditCustomListener={validateData()}
        binding.imgEditPhone.setOnClickDelay { onClickImageProfile() }
        binding.btnRegister.setOnClickButtonDelayListener{viewModel.register(binding.editEmailRegister,binding.btnRegister,file)}
        binding.editAddressRegister.uiEditClickListener = { addressResult.launch(null) }
    }

    private fun validateData(){
        viewModel.validateRegister(binding.editNameRegister,binding.editLastNameRegister, binding.editEmailRegister,
            binding.editPasswordRegister,binding.editPhonedRegister,binding.editAddressRegister,binding.btnRegister)
    }

    override fun observeViewModel() {
        viewModel.errorLiveData.observe(this,{
            binding.btnRegister.isButtonLoading = false
        })

        viewModel.successAccountLiveData.observe(this,{
             it?.apply {
                 HomeActivity.start(this@RegisterActivity)
             }
        })

        viewModel.successImageLiveData.observe(this,{
            showSnackBarCustom(binding.snackBarActivate,"foto guardada",colorBg = R.color.pink_200)
        })
    }

    private fun onClickImageProfile(){
       cameraManager?.doCamera()
    }

    override fun getValidActionToolBar() = true

    override fun getViewModel(): BaseViewModel = viewModel

    override fun onCameraPermissionDenied() {
        snackBarDeniedCamera(binding.snackBarActivate)
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        binding.imgProfile.setImageBitmap(img)
        file = File(path)
    }

    private fun configResult() {
        addressResult = registerForActivityResult(StartActivityContract(AddressActivity.start(this))) {
            it?.let {
                val place: PlaceModel? = it.getParcelable(DATA_ADDRESS_PLACE)
                place?.let { item ->
                    binding.editAddressRegister.uiText = item.name
                }
            }
        }
    }
}