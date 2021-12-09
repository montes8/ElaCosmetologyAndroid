package com.example.elacosmetologyandroid.ui.admin.banner

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.elacosmetologyandroid.databinding.FragmentBannerBinding
import com.example.elacosmetologyandroid.extensions.validateVisibility
import com.example.elacosmetologyandroid.model.CategoryModel
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.admin.ParametersActivity
import com.example.elacosmetologyandroid.ui.admin.ParametersViewModel
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class BannerFragment : BaseFragment() {

    private val viewModel: ParametersViewModel by viewModel(clazz = ParametersViewModel::class)
    private lateinit var binding: FragmentBannerBinding
    private var file: File? = null
    private var idCategory = EMPTY
    private var listCategory : List<CategoryModel> = ArrayList()

    companion object { fun newInstance() = BannerFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentBannerBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        viewModel.loadListCategory()
        configAction()
    }

    override fun setBundle() {}

    private fun configAction(){
        binding.btnAddImgBannerAdmin.setOnClickButtonDelayListener{(activity as ParametersActivity).loadCamera()}
        binding.btnSaveBannerAdmin.setOnClickButtonDelayListener{file?.let { viewModel.saveBanner(binding.btnSaveBannerAdmin,it) } }
        binding.editTitleBanner.uiEditCustomListener = {validateData()}
        binding.editDescriptionBanner.addTextChangedListener { validateData() }
    }

    override fun observeLiveData() {
        viewModel.errorLiveData.observe(this,{
            binding.nsvBanner.validateVisibility(true,binding.shimmerBanner)
            binding.btnSaveBannerAdmin.isButtonLoading = false
        })

        viewModel.successCategoriesLiveData.observe(this,{
            it.apply {
                configCategories(this)
            }
        })

        viewModel.successBannerLiveData.observe(this,{
            binding.btnSaveBannerAdmin.isButtonLoading = false
            (activity as ParametersActivity).showSuccessDialog()
        })
    }

    private fun validateData(){
        file?.let {
            viewModel.validateBanner(binding.editTitleBanner,idCategory,binding.editDescriptionBanner,binding.btnSaveBannerAdmin)
        }
    }

    override fun getViewModel(): BaseViewModel = viewModel

    private fun configCategories(list : List<CategoryModel>){
        listCategory = list
        if (list.isNotEmpty()){
           binding.editCategoryBanner.uiText = "Una categoria"

        }else{
            binding.editCategoryBanner.uiText = "Catgorias vacias"
        }
    }

     fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        binding.imgBannerTest.setImageBitmap(img)
        file = File(path)
        validateData()
    }
}