package com.example.elacosmetologyandroid.ui.admin.addcategory

import android.content.Context
import android.content.Intent
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.ActivityAddCategoryBinding
import com.example.elacosmetologyandroid.ui.BaseActivity
import com.example.elacosmetologyandroid.ui.BaseViewModel
import kotlinx.android.synthetic.main.mold_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddCategoryActivity : BaseActivity() {

    private val viewModel: CategoryViewModel by viewModel(clazz = CategoryViewModel::class)
    private lateinit var binding: ActivityAddCategoryBinding

    companion object { fun start(context: Context) { context.startActivity(Intent(context, AddCategoryActivity::class.java)) } }

    override fun getMainView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_category)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

    }

    override fun setUpView() {
        configAction()
    }

    private fun configAction(){
        txtTitleToolbar.text = getString(R.string.text_toolbar_category_add)
        binding.btnSaveCategory.setOnClickButtonDelayListener{viewModel.saveCategory(binding.btnSaveCategory)}
        binding.editNameAddCategory.uiEditCustomListener = {validateCategory()}
        binding.editDescriptionAddCategory.addTextChangedListener { validateCategory() }
    }

    override fun observeViewModel() {
        viewModel.successCategoryLiveData.observe(this,{
            binding.btnSaveCategory.isButtonLoading = false
            it?.apply { showSuccessDialogBase() }
        })

        viewModel.errorLiveData.observe(this,{ binding.btnSaveCategory.isButtonLoading = false})
    }

    private fun validateCategory(){
        viewModel.validateCategory(binding.editNameAddCategory,binding.editDescriptionAddCategory,binding.btnSaveCategory)

    }

    override fun getViewModel(): BaseViewModel = viewModel

    override fun getValidActionToolBar() = true

}