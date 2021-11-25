package com.example.elacosmetologyandroid.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.extensions.getError
import com.example.elacosmetologyandroid.extensions.gone
import com.example.elacosmetologyandroid.extensions.showDialogCustom
import com.example.elacosmetologyandroid.repository.network.exception.UnAuthorizedException
import com.example.elacosmetologyandroid.ui.login.LoginActivity
import kotlinx.android.synthetic.main.mold_toolbar.*

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getMainView()
    abstract fun setUpView()
    abstract fun observeLiveData()
    abstract fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>>?
    abstract fun getValidActionToolBar(): Boolean
    private val errorList = ArrayList<LiveData<Throwable>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMainView()
        setUpView()
        getActionToolbar(this.getValidActionToolBar())
        errorList.addAll(getErrorObservers() ?: ArrayList())
        observeErrors(errorList)
        observeLiveData()
    }

     fun observeErrors(errors: List<LiveData<Throwable>>) {
        for (error in errors) {
            error.observe(this, { showDialogError(error = it) })
        }
    }

    private fun showDialogError(error: Throwable) {
        val valueError = error.getError(this)
        showDialogCustom(R.layout.dialog_generic, true,title = valueError.second,description =
        valueError.third,icon = valueError.first,typeError = true) {
            if (error is UnAuthorizedException) {
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