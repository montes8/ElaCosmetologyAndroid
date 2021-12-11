package com.example.elacosmetologyandroid.ui.home.admin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.elacosmetologyandroid.databinding.FragmentAdminBinding
import com.example.elacosmetologyandroid.model.ItemModel
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.admin.addcategory.AddCategoryActivity
import com.example.elacosmetologyandroid.ui.admin.parameters.ParametersActivity
import com.example.elacosmetologyandroid.ui.home.admin.adapter.AdminAdapter
import com.example.elacosmetologyandroid.utils.CONFIG_DATA_ADMIN
import com.example.elacosmetologyandroid.utils.getData

class AdminFragment : BaseFragment() {

    private lateinit var binding: FragmentAdminBinding
    private var adapterAdmin = AdminAdapter()

    companion object { fun newInstance() = AdminFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentAdminBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpView() {
        binding.adapterAdmin = adapterAdmin
        adapterAdmin.adminList = getData(requireContext(), CONFIG_DATA_ADMIN)
        adapterAdmin.onClickAdmin = {configOnClickAdapter(it)}
    }

    private fun configOnClickAdapter(model : ItemModel){
        when(model.id){
            1 -> {ParametersActivity.start(requireContext())}
            2 ->{}
            3 -> {AddCategoryActivity.start(requireContext())}
            4 ->{}
        }
    }

    override fun setBundle() {}

    override fun observeLiveData() {}

    override fun getViewModel(): BaseViewModel? = null

}