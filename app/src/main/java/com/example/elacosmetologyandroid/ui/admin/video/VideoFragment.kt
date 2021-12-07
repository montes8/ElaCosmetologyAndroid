package com.example.elacosmetologyandroid.ui.admin.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.elacosmetologyandroid.databinding.FragmentVideoBinding
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel

class VideoFragment : BaseFragment() {

    private lateinit var binding: FragmentVideoBinding

    companion object { fun newInstance() = VideoFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentVideoBinding.inflate(inflater)
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