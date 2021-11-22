package com.example.elacosmetologyandroid.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.CrossDialog
import com.example.elacosmetologyandroid.component.CrossDialogBlock
import com.example.elacosmetologyandroid.repository.network.exception.CompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.exception.ApiException
import com.example.elacosmetologyandroid.repository.network.exception.NetworkException
import com.example.elacosmetologyandroid.repository.network.exception.UnAuthorizedException

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
            context.getString(R.string.error_general_title),
            this.mMessage
        )
        is CompleteErrorModel -> Triple(
            R.drawable.ic_info_error,
            title?:context.getString(R.string.error_internet),
            description?:context.getString(R.string.error_internet_description)
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

fun TextView.setColouredSpanClick(
    word: String,
    color: Int,
    isUnderLine: Boolean = false,
    block: () -> Unit
) {
    movementMethod = LinkMovementMethod.getInstance()
    val spannableString = SpannableString(text)
    val boldSpan = StyleSpan(Typeface.BOLD)
    val start = text.indexOf(word)
    val end = text.indexOf(word) + word.length
    try {
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                block()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = color
                ds.isUnderlineText = isUnderLine
            }
        }
        spannableString.setSpan(boldSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        text = spannableString
    } catch (e: IndexOutOfBoundsException) {
        println("'$word' was not not found in TextView text")
    }
}

fun isEmailValid(email: String): Boolean {
    return email.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun View.setOnClickDelay(time: Long = 700, onClick: () -> Unit) {
    this.setOnClickListener {
        it.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({ it.isEnabled = true }, time)
        onClick()
    }
}