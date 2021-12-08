package com.example.elacosmetologyandroid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.progress.ProgressFull
import com.example.elacosmetologyandroid.extensions.getError
import com.example.elacosmetologyandroid.extensions.gone
import com.example.elacosmetologyandroid.extensions.showDialogGeneric
import com.example.elacosmetologyandroid.repository.network.exception.UnAuthorizedException
import com.example.elacosmetologyandroid.ui.login.LoginActivity
import com.example.elacosmetologyandroid.usecases.usecases.AppUseCase
import kotlinx.android.synthetic.main.mold_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.inject
import org.koin.java.KoinJavaComponent.inject
import java.net.ProtocolException

abstract class BaseActivity : AppCompatActivity() {


    private val viewModel: AppViewModel by viewModel(clazz = AppViewModel::class)

    abstract fun getMainView()
    abstract fun setUpView()
    abstract fun observeViewModel()
    abstract fun getViewModel(): BaseViewModel?
    abstract fun getValidActionToolBar(): Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMainView()
        setUpView()
        getActionToolbar(this.getValidActionToolBar())
        observeMainViewModel()
    }


    private fun observeMainViewModel() {
        this.observeViewModel()
        getViewModel()?.let { viewModel ->
            viewModel.errorLiveData.observe(this,{ error ->
                this.showDialogError(error)
            })

            viewModel.loadingLiveData.observe(this, { it?.let { showLoading(it)} })

        }
    }

     fun showLoading(value: Boolean){
        if (value)ProgressFull.showCrossProgressFull(this) else ProgressFull.hideCrossProgressFull()
    }

    fun showDialogError(error: Throwable) {
        val valueError = error.getError(this)
        showDialogGeneric( true,title = valueError.second,description =
        valueError.third,icon = valueError.first,typeLotti = 1,closeVisibility = error !is UnAuthorizedException
        ) {
            if (error is UnAuthorizedException || error is ProtocolException) {
                viewModel.logout()
                goLogin()
            } else {
                if (!isDestroyed) {
                    onErrorDialogAccept()
                }
            }
        }
    }


    open fun onErrorDialogAccept() {}

    private fun goLogin() {
        LoginActivity.start(this)
        finishAffinity()
    }

    private fun getActionToolbar(value : Boolean){
        if (value){
            imgBackToolbar.setImageResource(R.drawable.ic_arrow_star)
            imgLogout.gone()
            imgBackToolbar.setOnClickListener {
                onBackPressed()
            }

        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.rigth_in, R.anim.right_out)
    }



}