package com.example.elacosmetologyandroid.ui.home

import android.content.Context
import android.content.Intent
import android.os.UserManager
import android.view.Menu
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityHomeBinding
import com.example.elacosmetologyandroid.extensions.addFragmentToNavigation
import com.example.elacosmetologyandroid.extensions.setImageString
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.home.admin.AdminFragment
import com.example.elacosmetologyandroid.ui.home.begin.BeginFragment
import com.example.elacosmetologyandroid.ui.home.order.OrderFragment
import com.example.elacosmetologyandroid.ui.home.product.ProductFragment
import com.example.elacosmetologyandroid.ui.login.LoginViewModel
import com.example.elacosmetologyandroid.utils.CONFIG_ITEM
import com.example.elacosmetologyandroid.utils.USER_ROLE
import com.example.elacosmetologyandroid.utils.getData
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        createItemNavigation()
        showFragment(beginFragment)
        setUpBottomNavigationView()
    }

    private fun createItemNavigation(){
        val listItem = getListItem()
        if (UserTemporary.getUser().rol == USER_ROLE)listItem?.take(3)
        listItem?.forEach {item->
            binding.btnNavigation.menu.add(Menu.NONE, item.id, Menu.NONE, item.title).icon =
                setImageString(item.icon,this)
        }
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
                1 -> showFragment(this.beginFragment)
                2 -> showFragment(this.productFragment)
                3 -> showFragment(this.orderFragment)
                4 -> showFragment(this.adminFragment)
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

    private fun getListItem(): List<ModelGeneric>? {
        return getData(this, CONFIG_ITEM)
    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()


}