package com.example.elacosmetologyandroid.ui.admin.parameters.banner

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.buttonSheet.GenericOptionBottomSheet
import com.example.elacosmetologyandroid.databinding.FragmentBannerBinding
import com.example.elacosmetologyandroid.extensions.gone
import com.example.elacosmetologyandroid.extensions.validateVisibility
import com.example.elacosmetologyandroid.extensions.visible
import com.example.elacosmetologyandroid.model.CategoryModel
import com.example.elacosmetologyandroid.model.ProductModel
import com.example.elacosmetologyandroid.ui.BaseFragment
import com.example.elacosmetologyandroid.ui.BaseViewModel
import com.example.elacosmetologyandroid.ui.admin.parameters.ParametersActivity
import com.example.elacosmetologyandroid.ui.admin.parameters.ParametersViewModel
import com.example.elacosmetologyandroid.utils.EMPTY
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File

class BannerFragment : BaseFragment() {

    private val viewModel: ParametersViewModel by viewModel(clazz = ParametersViewModel::class)
    private lateinit var binding: FragmentBannerBinding
    private var file: File? = null
    private var idCategory = EMPTY
    private var idProduct= EMPTY
    private var positionCategory = -1
    private var positionProduct = -1
    private var listCategory : List<CategoryModel> = ArrayList()
    private var listProduct : List<ProductModel> = ArrayList()

    companion object { fun newInstance() = BannerFragment() }

    override fun getMainView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentBannerBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setUpView() {
        loadCategory()
        configAction()
    }

    override fun setBundle() {}

    private fun configAction(){
        binding.btnAddImgBannerAdmin.setOnClickButtonDelayListener{(activity as ParametersActivity).loadCamera()}
        binding.btnSaveBannerAdmin.setOnClickButtonDelayListener{file?.let { viewModel.saveBanner(binding.btnSaveBannerAdmin,it) } }
        binding.editTitleBanner.uiEditCustomListener = {validateData()}
        binding.editCategoryBanner.uiEditClickListener = { if (listCategory.isNotEmpty()) showOptionCategory()}
        binding.editProductBanner.uiEditClickListener = { if (listCategory.isNotEmpty()) showOptionProduct()}
        binding.editDescriptionBanner.addTextChangedListener { validateData() }
    }

    override fun observeLiveData() {
        viewModel.errorLiveData.observe(this,{
            binding.nsvBanner.validateVisibility(true,binding.shimmerBanner)
            binding.btnSaveBannerAdmin.isButtonLoading = false
            binding.pbCategory.gone()
            binding.pbProduct.gone()
        })

        viewModel.successCategoriesLiveData.observe(this,{
            it.apply {
                binding.pbCategory.gone()
                configCategories(this)
            }
        })

        viewModel.successProductsLiveData.observe(this,{
            it.apply {
                binding.pbCategory.gone()
                configProduct(this)
            }
        })

        viewModel.successBannerLiveData.observe(this,{
            binding.btnSaveBannerAdmin.isButtonLoading = false
            (activity as ParametersActivity).showSuccessDialog()
        })
    }


    private fun showOptionCategory(){
        GenericOptionBottomSheet.newInstance(
                title = getString(R.string.txt_menu_category),
                itemList = listCategory,
                selectPosition = positionCategory
            ) {
            positionCategory = it
            binding.editCategoryBanner.uiText = listCategory[positionCategory].name
            loadProduct()

            }.show(childFragmentManager, GenericOptionBottomSheet::class.simpleName)
    }

    private fun showOptionProduct(){
        GenericOptionBottomSheet.newInstance(
            title = getString(R.string.txt_menu_product),
            itemList = listCategory,
            selectPosition = positionProduct
        ) {
            positionProduct = it
            binding.editProductBanner.uiText = listProduct[positionCategory].name

        }.show(childFragmentManager, GenericOptionBottomSheet::class.simpleName)

    }

    private fun validateData(){
        file?.let {
            viewModel.validateBanner(binding.editTitleBanner,idCategory,idProduct,binding.editDescriptionBanner,binding.btnSaveBannerAdmin)
        }
    }

    private fun loadProduct(){
        positionProduct = -1
        idProduct= EMPTY
        binding.editProductBanner.uiEnable = true
        binding.editProductBanner.uiText = "Selecione producto"
        binding.pbProduct.visible()
        viewModel.loadListProduct()

    }

    private fun loadCategory(){
        binding.pbCategory.visible()
        viewModel.loadListCategory()
    }


    override fun getViewModel(): BaseViewModel = viewModel

    private fun configCategories(list : List<CategoryModel>){
        listCategory = list
        if (list.isNotEmpty()){
           binding.editCategoryBanner.uiText = "Selecione Categoria"

        }else{
            binding.editCategoryBanner.uiText = "Debes agregar categorias"
        }
    }

    private fun configProduct(list : List<ProductModel>){
        listProduct = list
        if (list.isNotEmpty()){
            binding.editProductBanner.uiText = "Selecione Producto"
        }else{
            binding.editProductBanner.uiText = "Debes agregar productos"
        }
    }

     fun onGetImageCameraCompleted(path: String, img: Bitmap) {
        binding.imgBannerTest.setImageBitmap(img)
        file = File(path)
        validateData()
    }
}