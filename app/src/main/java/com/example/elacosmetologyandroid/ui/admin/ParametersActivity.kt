package com.example.elacosmetologyandroid.ui.admin

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityParametersBinding
import com.example.elacosmetologyandroid.extensions.*
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.admin.banner.BannerFragment
import com.example.elacosmetologyandroid.ui.admin.param.ParamFragment
import com.example.elacosmetologyandroid.ui.admin.video.VideoFragment
import com.example.elacosmetologyandroid.utils.NAME_PATH_PROFILE
import com.example.elacosmetologyandroid.utils.controller.CameraController
import com.google.android.material.tabs.TabLayout

class ParametersActivity : BaseActivity(), CameraController.CameraControllerListener {

    private lateinit var binding: ActivityParametersBinding
    private var currentFragment: BaseFragment? = null
    private lateinit var paramFragment   : ParamFragment
    private lateinit var videoFragment : VideoFragment
    private lateinit var bannerFragment   : BannerFragment
    private var cameraManager: CameraController? = null

    companion object { fun start(context: Context) { context.startActivity(Intent(context, ParametersActivity::class.java)) } }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parameters)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        initFragment()
        cameraManager = CameraController(this, NAME_PATH_PROFILE, this)
        binding.toolbarParam.txtTitleToolbar.text = "Configuraciones"
    }

    override fun observeViewModel() {}


    private fun configTab(){
        binding.tabInsurance.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.run { selectedTab(position) }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun selectedTab(position : Int){
        if(position != 1)configItemFragmentMovie()
        when(position){
            0 ->{showFragment(paramFragment)}
            1 ->{showFragment(videoFragment)}
            2 ->{showFragment(bannerFragment)}
        }
    }

    private fun initFragment(){
        paramFragment = ParamFragment.newInstance()
        videoFragment = VideoFragment.newInstance()
        bannerFragment = BannerFragment.newInstance()
        showFragment(paramFragment)
        configTab()
    }

    private fun showFragment(fragment: BaseFragment):Boolean{
        if (this.currentFragment != fragment) {
            this.supportFragmentManager.let {
                it.addFragmentToNavigation(
                    fragment,
                    fragment::class.java.name,
                    R.id.fragmentContentParam,
                    this.currentFragment
                )
                this.validateCurrentFragmentInstance(fragment)
            }
        } else {
            if (this.currentFragment?.isVisible == true)
                this.currentFragment?.backToFirstFragmentOfNavigation()
        }
        return true
    }

    fun showSuccessDialog(){
        showDialogGeneric(true,imageVisibility = false,title = getString(R.string.text_update_data_param),
            description = getString(R.string.text_des_update_data_param),
            btnTextAccepted = getString(R.string.txt_end_up),btnTextNegative = getString(R.string.txt_continue)) {
            finish()
        }
    }

    fun errorSnackBarSaveData(){
        showSnackBarCustom(binding.snackBarParameters,getString(R.string.error_general_title),colorBg = R.color.red)
    }

    private fun validateCurrentFragmentInstance(fragment: Fragment) {
        if (fragment is BaseFragment) this.currentFragment = fragment
    }

    private fun configItemFragmentMovie(){
        supportFragmentManager.let {
            it.findFragmentByTag(VideoFragment::class.java.name)?.let { fragment ->
                fragment as VideoFragment
                fragment.setStopVideo()
            }
        }
    }

    fun loadCamera(){
        cameraManager?.doCamera()
    }

    override fun onCameraPermissionDenied() {
        snackBarDeniedCamera(binding.snackBarParameters)
    }

    override fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        supportFragmentManager.let {
            it.findFragmentByTag(BannerFragment::class.java.name)?.let { fragment ->
                fragment as BannerFragment
                fragment.onGetImageCameraCompleted(path,img)
            }
        }
    }

    override fun onBackPressed() { finish() }

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = true

}