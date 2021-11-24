package com.example.elacosmetologyandroid.ui.home.begin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.databinding.FragmentBeginBinding
import com.example.elacosmetologyandroid.ui.BaseFragment


class BeginFragment : BaseFragment() {

    private lateinit var binding: FragmentBeginBinding

    companion object {
        fun newInstance() = BeginFragment().apply {
            arguments = Bundle().apply {
                //putInt(MENU_HOME_ID, menuId)
               // putParcelable(USER, user)
            }
        }

    }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentBeginBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
    }

    override fun observeLiveData() {
    }

    override fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>> = arrayListOf()

}