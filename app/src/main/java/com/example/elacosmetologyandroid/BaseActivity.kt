package com.example.elacosmetologyandroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.component.ProgressButton

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getMainView()
    abstract fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>>?
    abstract fun setUpView()
    abstract fun observeLiveData()
    private val errorList = ArrayList<LiveData<Throwable>>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMainView()
        setUpView()
        errorList.addAll(getErrorObservers() ?: ArrayList())
        observeErrors(errorList)
        observeErrorsAnalytics(errorAnalytics)
        observeLiveData()
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
                        if (!isDestroyed) {
                            onErrorDialogAccept()
                        }
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



}