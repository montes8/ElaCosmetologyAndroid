package com.example.elacosmetologyandroid.ui.admin.param

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.elacosmetologyandroid.databinding.FragmentParamBinding
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel

class ParamFragment : BaseFragment() {

    private lateinit var binding: FragmentParamBinding

    companion object { fun newInstance() = ParamFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentParamBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
    }

    override fun setBundle() {}

    override fun observeLiveData() {}

    override fun getViewModel(): BaseViewModel? = null
}