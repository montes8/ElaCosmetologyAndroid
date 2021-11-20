package com.example.elacosmetologyandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.rimac.components.button.ProgressButton
import com.rimac.repository.network.exceptions.ApiException
import com.rimac.repository.network.exceptions.NetworkException
import com.rimac.rimac_surrogas.R
import com.rimac.rimac_surrogas.components.ProgressDialog
import com.rimac.rimac_surrogas.manager.SessionManager
import com.rimac.rimac_surrogas.ui.config.ConfigViewModel
import com.rimac.rimac_surrogas.ui.login.LoginActivity
import com.rimac.rimac_surrogas.util.*
import com.rimac.rimac_surrogas.util.adobe.*
import com.rimac.usecases.util.UnAuthorizedException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getMainView()
    abstract fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>>?
    abstract fun getErrorAnalytics(): ArrayList<MutableLiveData<Throwable>>?
    abstract fun setUpView()
    abstract fun observeLiveData()
    private val errorList = ArrayList<LiveData<Throwable>>()
    private val errorAnalytics = ArrayList<LiveData<Throwable>>()
    private val progressDialog = ProgressDialog()
    private var errorAnalyticsCode: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMainView()
        setUpView()
        errorList.addAll(getErrorObservers() ?: ArrayList())
        errorAnalytics.addAll(getErrorAnalytics() ?: ArrayList())
        observeErrors(errorList)
        observeErrorsAnalytics(errorAnalytics)
        observeLiveData()
        validateSession()
    }

    fun observeErrors(errors: List<LiveData<Throwable>>) {
        for (error in errors) {
            error.observe(this, {
                showDialogError(error = it)
            })
        }
    }



    private fun showDialogError(error: Throwable) {
        val valueError = error.getError(this)
        showCrossDialog(R.layout.ui_kit_cross_dialog, true) { dialog ->
            dialog.mView.findViewById<View>(R.id.dialog_close)
                .setOnClickListener { dialog.dismiss() }
            dialog.mView.findViewById<ImageView>(R.id.dialog_icon)
                .setImageResource(valueError.first)
            dialog.mView.findViewById<TextView>(R.id.dialog_text_title).text = valueError.second
            dialog.mView.findViewById<TextView>(R.id.dialog_text_description).text =
                valueError.third
            dialog.mView.findViewById<ProgressButton>(R.id.dialog_btn_accept)
                .setOnClickButtonListener {
                    if (error is UnAuthorizedException) {
                        goLogin(true)
                      dialog.dismiss()
                    } else {
                        dialog.dismiss()
                    }
                }
        }
    }

    /**
     * Metodo para realizar una accion cuando se acepte el dialogo de error.
     */
    open fun onErrorDialogAccept() {}

    fun goLogin(isUnAuthorized: Boolean) {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        })
        finishAffinity()
    }

    fun showProgress(isShow: Boolean) {
        if (isShow) progressDialog.show(
            this.supportFragmentManager,
            ProgressDialog::class.java.name
        )
        else progressDialog.dismiss()
    }




    fun showProgressSafely() {
        if (!progressDialog.isVisible) {
            showProgress(true)
        }
    }

    fun hideProgressSafely() {
        if (progressDialog.isVisible) {
            showProgress(false)
        }
    }

}