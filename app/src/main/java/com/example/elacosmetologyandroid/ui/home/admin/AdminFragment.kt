package com.example.elacosmetologyandroid.ui.home.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentAdminBinding
import com.example.elacosmetologyandroid.ui.BaseFragment

class AdminFragment : BaseFragment() {


    private lateinit var binding: FragmentAdminBinding

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentAdminBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
    }

    override fun observeLiveData() {

    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()

}