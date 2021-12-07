package com.example.elacosmetologyandroid.ui.admin.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.elacosmetologyandroid.databinding.FragmentBannerBinding
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel

class BannerFragment : BaseFragment() {

    private lateinit var binding: FragmentBannerBinding

    companion object { fun newInstance() = BannerFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentBannerBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
    }

    override fun setBundle() {
    }

    override fun observeLiveData() {
    }

    override fun getViewModel(): BaseViewModel? = null
}