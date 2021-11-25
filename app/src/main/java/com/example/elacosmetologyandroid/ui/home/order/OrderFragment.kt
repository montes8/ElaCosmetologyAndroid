package com.example.elacosmetologyandroid.ui.home.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentOrderBinding
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel

class OrderFragment : BaseFragment() {

    private lateinit var binding: FragmentOrderBinding


    companion object {
        fun newInstance() = OrderFragment().apply {
            arguments = Bundle().apply {
                //putInt(MENU_HOME_ID, menuId)
                // putParcelable(USER, user)
            }
        }

    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentOrderBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
    }

    override fun observeLiveData() {
    }

    override fun getViewModel(): BaseViewModel? = null

}