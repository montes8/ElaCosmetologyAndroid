package com.example.elacosmetologyandroid.extensions

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.CrossDialog
import com.example.elacosmetologyandroid.component.CrossDialogBlock
import com.example.elacosmetologyandroid.repository.ApiException
import com.example.elacosmetologyandroid.repository.NetworkException
import com.example.elacosmetologyandroid.repository.UnAuthorizedException
import com.example.elacosmetologyandroid.repository.UserNotFoundException

fun View.visible() = apply {
    visibility = View.VISIBLE
}

fun View.gone() = apply {
    visibility = View.GONE
}

fun View.validateVisibility(value: Boolean) {
    if (value) visible() else gone()
}

fun AppCompatActivity.showCrossDialog(
    layout: Int,
    cancelable: Boolean = true,
    func: CrossDialogBlock
) {
    val dialog = CrossDialog(layout, func)
    dialog.dialog?.setCancelable(cancelable)
    dialog.isCancelable = cancelable
    dialog.show(this.supportFragmentManager, CrossDialog::class.java.name)
}


fun Throwable.getError(context: Context): Triple<Int, String, String> {
    return when (this) {
        is ApiException -> Triple(
            R.drawable.ic_info_error,
            this.title,
            this.mMessage
        )
        is UserNotFoundException -> Triple(
            R.drawable.ic_info_error,
            context.getString(R.string.error_session),
            context.getString(R.string.error_general)
        )
        is NetworkException -> Triple(
            R.drawable.ic_info_error,
            context.getString(R.string.error_internet),
            context.getString(R.string.error_internet_description)
        )
        is UnAuthorizedException -> Triple(
            R.drawable.ic_info_error,
            context.getString(R.string.user_unauthorized_title),
            context.getString(R.string.user_unauthorized_description)
        )
        else -> Triple(
            R.drawable.ic_info_error,
            context.getString(R.string.error_general_title),
            context.getString(R.string.error_general)
        )
    }
}

fun Context.hideKeyboardFrom(view: View) {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
