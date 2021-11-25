package com.example.elacosmetologyandroid.ui.home.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentOrderBinding
import com.example.elacosmetologyandroid.databinding.FragmentProductBinding
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel

class ProductFragment : BaseFragment() {

    private lateinit var binding : FragmentProductBinding

    companion object {
        fun newInstance() =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    // putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentProductBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
    }

    override fun observeLiveData() {
    }

    override fun getViewModel(): BaseViewModel? = null

}