package com.example.elacosmetologyandroid.ui.home

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityHomeBinding
import com.example.elacosmetologyandroid.extensions.*
import com.example.elacosmetologyandroid.manager.UserTemporary
import com.example.elacosmetologyandroid.model.ModelGeneric
import com.example.elacosmetologyandroid.model.User
import com.example.elacosmetologyandroid.ui.AppViewModel
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.home.admin.AdminFragment
import com.example.elacosmetologyandroid.ui.home.begin.BeginFragment
import com.example.elacosmetologyandroid.ui.home.order.OrderFragment
import com.example.elacosmetologyandroid.ui.home.product.ProductFragment
import com.example.elacosmetologyandroid.ui.login.LoginActivity
import com.example.elacosmetologyandroid.utils.CONFIG_ITEM
import com.example.elacosmetologyandroid.utils.EMPTY
import com.example.elacosmetologyandroid.utils.USER_ROLE
import com.example.elacosmetologyandroid.utils.getData
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.nav_header_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val viewModel: AppViewModel by viewModel(clazz = AppViewModel::class)

    private lateinit var binding: ActivityHomeBinding
    private var currentFragment: BaseFragment? = null

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
        configAction()
    }

    private fun createItemNavigation(user : User?){
        binding.navViewMenu.setNavigationItemSelectedListener(this)
        val listItem : ArrayList<ModelGeneric> = getListItem() as ArrayList<ModelGeneric>
        user?.let {
            if (it.rol == USER_ROLE)listItem.removeAt(3)
        }?:listItem.removeAt(3)

        listItem.forEach {item->
            binding.btnNavigation.menu.add(Menu.NONE, item.id, Menu.NONE, item.title).icon =
                setImageString(item.icon,this)
        }
        configTitleToolbar(1)
        showFragment(BeginFragment.newInstance())
        setUpBottomNavigationView()
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


    private fun configAction(){
        binding.toolBarHome.imgLogout.setOnClickDelay {
           dialogLogout()
        }
        binding.toolBarHome.imgBackToolbar.setOnClickDelay{
            drawerVisible()
        }

        Handler(Looper.getMainLooper()).postDelayed({  configDataUser()}, 200)
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

    override fun observeViewModel() {}

    private fun configDataUser(){
        UserTemporary.getUser()?.let { txtNameUserMenu.text = it.name }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navProfile -> {
                selectedItemMenu(item,true)
                //ProfileActivity.newInstance(this)
            }

            R.id.navPromotions -> {
                selectedItemMenu(item,false)
                //FavoriteActivity.newInstance(this)
            }

            R.id.navMap -> {
                selectedItemMenu(item,false)

               // MapActivity.newInstance(this,listEstablishment,queryLocation,true)
            }

            R.id.navFacebook -> {
                selectedItemMenu(item, false)
                //validateFacebookUrl(this)
            }

            R.id.navOther ->{
                selectedItemMenu(item,false)
                //TermsActivity.newInstance(this)
            }

            R.id.navLogout -> {
                selectedItemMenu(item,false)
                dialogLogout()
            }

        }
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
        drawerGone()
        return true
    }



    private fun dialogLogout(){
        showDialogCustom(R.layout.dialog_generic, true,typeError = false) {
           viewModel.logout()
            LoginActivity.start(this@HomeActivity)
        }
    }

    private fun drawerVisible() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun drawerGone() {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun selectedItemMenu(item : MenuItem, value : Boolean){
        item.isChecked = value
    }

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

    override fun getViewModel(): BaseViewModel? = null

    override fun getValidActionToolBar() = false

}