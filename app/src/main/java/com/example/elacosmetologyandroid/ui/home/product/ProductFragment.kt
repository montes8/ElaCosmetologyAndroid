package com.example.elacosmetologyandroid.ui.home.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentOrderBinding
import com.example.elacosmetologyandroid.ui.BaseFragment

class ProductFragment : BaseFragment() {

    private lateinit var binding : FragmentOrderBinding

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
        binding = FragmentOrderBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
    }

    override fun observeLiveData() {
    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()

}