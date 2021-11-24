package com.example.elacosmetologyandroid.ui.home

import android.content.Context
import android.content.Intent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityHomeBinding
import com.example.elacosmetologyandroid.extensions.addFragmentToNavigation
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.home.admin.AdminFragment
import com.example.elacosmetologyandroid.ui.home.begin.BeginFragment
import com.example.elacosmetologyandroid.ui.home.order.OrderFragment
import com.example.elacosmetologyandroid.ui.home.product.ProductFragment

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var currentFragment: BaseFragment? = null
    private lateinit var beginFragment: BeginFragment
    private lateinit var productFragment: ProductFragment
    private lateinit var orderFragment: OrderFragment
    private lateinit var adminFragment: AdminFragment

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.lifecycleOwner = this
    }

    override fun setUpView() {
        configTabNavigation()
    }

    private fun configTabNavigation(){
        initFragment()
        showFragment(beginFragment)
        setUpBottomNavigationView()
    }

    private fun initFragment(){
        beginFragment   = BeginFragment.newInstance()
        productFragment = ProductFragment.newInstance()
        orderFragment   = OrderFragment.newInstance()
        adminFragment   = AdminFragment.newInstance()
    }

    private fun setUpBottomNavigationView() {
        binding.btnNavigation.setOnItemSelectedListener { item ->
            val isSelected = when (item.itemId) {
                R.id.bottom_nav_home -> showFragment(this.beginFragment)
                R.id.bottom_nav_product -> showFragment(this.productFragment)
                R.id.bottom_nav_order -> showFragment(this.orderFragment)
                R.id.bottom_nav_admin -> showFragment(this.adminFragment)
                else -> false
            }
            isSelected
        }
    }

    override fun observeLiveData() {

    }


    private fun showFragment(fragment: BaseFragment): Boolean {
        if (this.currentFragment != fragment) {
            this.supportFragmentManager.let {
                it.addFragmentToNavigation(
                    fragment,
                    fragment::class.java.name,
                    R.id.fragmentContent,
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

    private fun validateCurrentFragmentInstance(fragment: Fragment) {
        if (fragment is BaseFragment) this.currentFragment = fragment
    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()


}