package com.example.elacosmetologyandroid.ui.home

import android.content.Context
import android.content.Intent
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
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.home.admin.AdminFragment
import com.example.elacosmetologyandroid.ui.home.begin.BeginFragment
import com.example.elacosmetologyandroid.ui.home.order.OrderFragment
import com.example.elacosmetologyandroid.ui.home.product.ProductFragment
import com.example.elacosmetologyandroid.utils.CONFIG_ITEM
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.USER_ROLE
import com.example.elacosmetologyandroid.utils.getData

class HomeActivity : BaseActivity() {

    private lateinit var binding: ActivityHomeBinding
     var currentFragment: BaseFragment? = null

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
        createItemNavigation(UserTemporary.getUser())
        showFragment(BeginFragment.newInstance())
        configTitleToolbar(1)
        setUpBottomNavigationView()
    }

    private fun createItemNavigation(user : User?){
        val listItem : ArrayList<ModelGeneric> = getListItem() as ArrayList<ModelGeneric>
        user?.let { if (it.rol == USER_ROLE)listItem.removeAt(3)
        }?:listItem.removeAt(3)

        listItem.forEach {item->
            binding.btnNavigation.menu.add(Menu.NONE, item.id, Menu.NONE, item.title).icon =
                setImageString(item.icon,this)
        }
    }

    private fun setUpBottomNavigationView() {
        binding.btnNavigation.setOnItemSelectedListener { item ->
            if(item.itemId != 1)configItemFragmentMovie()
            configTitleToolbar(item.itemId)
            val isSelected = when (item.itemId) {
                1 -> {showFragment(BeginFragment.newInstance()) }
                2 -> showFragment(ProductFragment.newInstance())
                3 -> showFragment(OrderFragment.newInstance())
                4 -> showFragment(AdminFragment.newInstance())
                else -> false
            }
            isSelected
        }
    }

    private fun configTitleToolbar(id : Int){
        binding.toolBarHome.txtTitleToolbar.text = when (id) {
            1 -> getString(R.string.txt_home)
            2 -> {getString(R.string.txt_product) }
            3 -> {getString(R.string.txt_order) }
            4 -> {getString(R.string.txt_admin) }
            else -> EMPTY
        }

    }

    override fun observeLiveData() {}


    private fun configItemFragmentMovie(){
        supportFragmentManager.let {
            val fragment = it.findFragmentByTag(BeginFragment::class.java.name) as BeginFragment
            fragment.setStopVideo()
        }
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