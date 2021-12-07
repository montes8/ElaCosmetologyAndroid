package com.example.elacosmetologyandroid.ui.admin

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityParametersBinding
import com.example.elacosmetologyandroid.extensions.addFragmentToNavigation
import com.example.elacosmetologyandroid.extensions.replaceFragmentToNavigation
import com.example.elacosmetologyandroid.extensions.showDialogGeneric
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.admin.banner.BannerFragment
import com.example.elacosmetologyandroid.ui.admin.param.ParamFragment
import com.example.elacosmetologyandroid.ui.admin.video.VideoFragment
import com.example.elacosmetologyandroid.ui.home.begin.BeginFragment
import com.example.elacosmetologyandroid.ui.home.order.OrderFragment
import com.example.elacosmetologyandroid.ui.home.product.ProductFragment
import com.example.elacosmetologyandroid.ui.login.LoginActivity
import com.example.elacosmetologyandroid.utils.EMPTY
import com.google.android.material.tabs.TabLayout

class ParametersActivity : BaseActivity() {

    private lateinit var binding: ActivityParametersBinding

    private lateinit var paramFragment   : ParamFragment
    private lateinit var videoFragment : VideoFragment
    private lateinit var bannerFragment   : BannerFragment

    companion object { fun start(context: Context) { context.startActivity(Intent(context, ParametersActivity::class.java)) } }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_parameters)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        initFragment()
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
        when(position){
            0 ->{showFragment(paramFragment)}
            1 ->{showFragment(videoFragment)}
            2 ->{showFragment(bannerFragment)}
        }
    }

    private fun initFragment(){
        paramFragment = ParamFragment.newInstance()
        showFragment(paramFragment)
        configTab()
    }

    private fun showFragment(fragment: BaseFragment){
        this.supportFragmentManager.let {
            it.replaceFragmentToNavigation(
                fragment,
                fragment::class.java.name,
                R.id.fragmentContentParam,
            )
        }
    }

    fun showSuccessDialog(){
        showDialogGeneric(true,imageVisibility = false,title = getString(R.string.text_update_data_param),
            description = getString(R.string.text_des_update_data_param),
            btnTextAccepted = getString(R.string.txt_end_up),btnTextNegative = getString(R.string.txt_continue)) {
            finish()
        }
    }

    override fun onBackPressed() { finish() }

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = true

}