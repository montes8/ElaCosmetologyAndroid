package com.example.elacosmetologyandroid.ui.home.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentAdminBinding
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel


class AdminFragment : BaseFragment() {


    private lateinit var binding: FragmentAdminBinding

    companion object {
        fun newInstance() = AdminFragment().apply {
            arguments = Bundle().apply {
                //putInt(MENU_HOME_ID, menuId)
                // putParcelable(USER, user)
            }
        }

    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentAdminBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
    }

    override fun setBundle() {}

    override fun observeLiveData() {

    }

    override fun getViewModel(): BaseViewModel? = null

}