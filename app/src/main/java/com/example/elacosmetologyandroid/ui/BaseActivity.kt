package com.example.elacosmetologyandroid.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.ProgressButton
import com.example.elacosmetologyandroid.extensions.getError
import com.example.elacosmetologyandroid.extensions.showCrossDialog
import com.example.elacosmetologyandroid.repository.UnAuthorizedException
import com.example.elacosmetologyandroid.ui.login.LoginActivity

abstract class BaseActivity : AppCompatActivity() {
    abstract fun getMainView()
    abstract fun setUpView()
    abstract fun observeLiveData()
    abstract fun getErrorObservers(): ArrayList<MutableLiveData<Throwable>>?
    private val errorList = ArrayList<LiveData<Throwable>>()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMainView()
        setUpView()
        errorList.addAll(getErrorObservers() ?: ArrayList())
        observeErrors(errorList)
        observeLiveData()
    }

    private fun observeErrors(errors: List<LiveData<Throwable>>) {
        for (error in errors) {
            error.observe(this, {
                showDialogError(error = it)
            })
        }
    }



    private fun showDialogError(error: Throwable) {
        val valueError = error.getError(this)
        showCrossDialog(R.layout.cross_dialog, true) { dialog ->
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
                        goLogin()
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


    open fun onErrorDialogAccept() {}

    private fun goLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        })
        finishAffinity()
    }



}