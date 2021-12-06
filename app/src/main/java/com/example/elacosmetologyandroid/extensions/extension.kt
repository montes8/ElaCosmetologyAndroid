package com.example.elacosmetologyandroid.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.elacosmetologyandroid.BuildConfig
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.calendar.DatePickerDialog
import com.example.elacosmetologyandroid.component.dialog.GenericDialog
import com.example.elacosmetologyandroid.component.snackbar.SnackBarStatus
import com.example.elacosmetologyandroid.repository.network.exception.ApiException
import com.example.elacosmetologyandroid.repository.network.exception.CompleteErrorModel
import com.example.elacosmetologyandroid.repository.network.exception.NetworkException
import com.example.elacosmetologyandroid.repository.network.exception.UnAuthorizedException
import com.example.elacosmetologyandroid.utils.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.Serializable
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


fun View.visible() = apply {
    visibility = View.VISIBLE
}

fun View.invisible() = apply {
    visibility = View.INVISIBLE
}


fun View.gone() = apply {
    visibility = View.GONE
}

fun View.validateVisibility(value: Boolean) {
    if (value) visible() else gone()
}

fun View.validatePairVisibility(value: Boolean,view: View) {
    if (value) {
        this.visible()
        view.invisible()
    }else {
        this.invisible()
        view.visible()
    }
}

fun View.validateVisibility(value: Boolean,view: View) {
    if (value) {
        this.visible()
        view.gone()
    }else {
        this.gone()
        view.visible()
    }
}

fun AppCompatActivity.showDialogGeneric(
    cancelable: Boolean = true,
    title: String = TITLE_DIALOG_DEFAULT, description: String = TITLE_DESCRIPTION_DEFAULT,
    icon: Int = R.drawable.ic_info_error, imageVisibility: Boolean = true,closeVisibility : Boolean = true,
    typeLotti :Int = 0,btnTextAccepted : String = getString(R.string.dialog_accept),btnTextNegative : String = getString(R.string.txt_cancel_btn),
    func: GenericDialog.() -> Unit
) {
    val dialog = GenericDialog(title = title, description = description, icon = icon, imageVisibility = imageVisibility,closeVisibility = closeVisibility
        ,typeLotti = typeLotti,btnTextAccepted = btnTextAccepted,btnTextNegative = btnTextNegative
    ) { func() }
    dialog.dialog?.setCancelable(cancelable)
    dialog.isCancelable = cancelable
    dialog.show(this.supportFragmentManager, GenericDialog::class.java.name)

}


fun AppCompatActivity.showDialogDatePiker(
   dateDefault: Long = 0, dateOld: Long = 0, future: Int = 100, typeCalendar: Int = DatePickerDialog.MODE_FULL,
   func: (Pair<Date,Int>) -> Unit
) {
    val dialog = DatePickerDialog(
        dateDefault = dateDefault, dateOld = dateOld, future = future, typeCalendar = typeCalendar
    ) {
        func(Pair(it.first,it.second))
    }

    dialog.show(this.supportFragmentManager, GenericDialog::class.java.name)

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
            title ?: context.getString(R.string.error_internet),
            description ?: context.getString(R.string.error_internet_description)
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

fun View.setOnClickDelay(time: Long = 700, onClick: (View) -> Unit) {
    this.setOnClickListener {
        it.isEnabled = false
        Handler(Looper.getMainLooper()).postDelayed({ it.isEnabled = true }, time)
        onClick(it)
    }
}

fun FragmentManager?.addFragmentToNavigation(
    fragment: Fragment,
    tag: String,
    containerId: Int,
    currentFragment: Fragment? = null
) {
    this?.let {
        if (!it.fragmentIsAdded(fragment)) {
            it.beginTransaction().let { transaction ->
                transaction.add(containerId, fragment, tag)
                currentFragment?.let { cFragment -> transaction.hide(cFragment) }
                transaction.addToBackStack(tag)
                transaction.commit()
            }
        } else showExistingFragment(fragment, currentFragment)
    }
}

fun FragmentManager?.replaceFragmentToNavigation(
    fragment: Fragment,
    tag: String,
    containerId: Int,
    currentFragment: Fragment? = null
) {
    this?.let {
        if (!it.fragmentIsAdded(fragment)) {
            it.beginTransaction().let { transaction ->
                transaction.replace(containerId, fragment, tag)
                currentFragment?.let { cFragment -> transaction.hide(cFragment) }
                transaction.addToBackStack(tag)
                transaction.commit()
            }
        } else showExistingFragment(fragment, currentFragment)
    }
}

fun FragmentManager?.fragmentIsAdded(fragment: Fragment): Boolean {
    return this?.let { return !it.fragments.isNullOrEmpty() && it.fragments.contains(fragment) }
        ?: false
}

fun FragmentManager?.showExistingFragment(fragment: Fragment, currentFragment: Fragment? = null) {
    this?.let {
        it.beginTransaction().let { transaction ->
            transaction.show(fragment)
            currentFragment?.let { transaction.hide(currentFragment) }
            transaction.commit()
        }
    }
}

fun setImageString(value: String, context: Context):Drawable?{
    val uri = "@drawable/$value"
    val imageResource: Int = context.resources.getIdentifier(uri, null, context.packageName)
    return ContextCompat.getDrawable(context, imageResource)

}

fun setHighLightedText(textView: TextView, textToHighlight: String) {
    val tvt = textView.text.toString()
    var ofe = tvt.indexOf(textToHighlight, 0)
    val wordToSpan: Spannable = SpannableString(textView.text)
    var ofs = 0
    while (ofs < tvt.length && ofe != -1) {
        ofe = tvt.indexOf(textToHighlight, ofs)
        if (ofe == -1) break else {
            // set color here
            wordToSpan.setSpan(
                BackgroundColorSpan(ContextCompat.getColor(textView.context, R.color.pink_50)),
                ofe,
                ofe + textToHighlight.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            textView.setText(wordToSpan, TextView.BufferType.SPANNABLE)
        }
        ofs = ofe + 1
    }
}

fun FragmentManager?.moveBackToFirstFragment(currentFragment: Fragment?): Fragment? {
    this?.let {
        currentFragment?.let { cFragment ->
            if (it.fragments.size > 1 && it.fragments.first() != cFragment) {
                it.beginTransaction().let { transaction ->
                    transaction.show(it.fragments.first())
                    transaction.hide(cFragment)
                    transaction.commit()
                }
                return it.fragments.first()
            }
        }
    }
    return null
}

fun showMaterialDialog(context: Context,message :String = TEXT_CLOSE_APP_, textBtnAccept :String= TEXT_CONFIRM,
                       textBtnNegative:String = TEXT_CANCEL, func: () -> Unit) {
    val dialogCustom = AlertDialog.Builder(context)
    dialogCustom.setTitle(context.resources.getString(R.string.app_name_use_app))
    dialogCustom.setMessage(message)
    dialogCustom.setCancelable(false)
    dialogCustom.setPositiveButton(textBtnAccept) { dialog, _ ->
        func()
        dialog.dismiss()
    }
    dialogCustom.setNegativeButton(textBtnNegative) { dialog, _ ->
        dialog.dismiss()
    }
    dialogCustom.show()
}

fun loadImageUrlPicasso(url: String,view: ImageView,icon : Boolean = true) {
    if (url.isNotEmpty()) {
        Picasso.get()
            .load(url).placeholder(if(icon)R.drawable.shape_place_holder else R.drawable.ic_profile_place_holder)
            .into(view,object :Callback{
                override fun onSuccess() {}

                override fun onError(e: Exception?) {
                    view.setImageResource(if(icon)R.drawable.shape_place_holder else R.drawable.ic_profile_place_holder)
                }
            })
    }
}

fun ImageView.urlCustomImage(type : String,id : String,icon : Boolean = true){
    loadImageUrlPicasso("${BuildConfig.BASE_URL}api/uploads/$type/$id",this,icon)
}

fun ImageView.urlCustomImageBanner(type : String,id : String,icon : Boolean = true){
    loadImageUrlPicasso("${BuildConfig.BASE_URL}api/uploads/banner/$type/$id",this,icon)
}

fun View.uiValidateVisibilityTwoView(value: Boolean, view: View) {
    if (value) {
        this.visible()
        view.gone()
    } else {
        this.gone()
        view.visible()
    }
}

fun View.setMargins(
    left: Int? = null,
    top: Int? = null,
    right: Int? = null,
    bottom: Int? = null
) {
    val lp = layoutParams as? ViewGroup.MarginLayoutParams
        ?: return

    lp.setMargins(
        left ?: lp.leftMargin,
        top ?: lp.topMargin,
        right ?: lp.rightMargin,
        bottom ?: lp.bottomMargin
    )

    layoutParams = lp
}


fun showSnackBarCustom(viewIdentifier: View,message: String = ERROR_TEXT,duration : Int = 1500,colorBg : Int = R.color.pink_200){
        SnackBarStatus.findSuitableParent(viewIdentifier)?.let { view ->
            SnackBarStatus.make(
                viewGroup = view,
                message = message,
                duration = duration,
                icon = R.drawable.ic_success,
                backgroundColor = colorBg,
                upIcon = false
            ).show()
        }
}



class StartActivityContract<T>(
    private val intent: Intent,
    private val inputNameExtra: String = ""
) :
    ActivityResultContract<T, Bundle?>() where  T : Serializable {
    override fun createIntent(context: Context, input: T?): Intent = intent.apply {
        input?.let {
            putExtra(inputNameExtra, it)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Bundle? {
        return when (resultCode) {
            Activity.RESULT_OK -> intent?.extras
            else -> null
        }
    }
}


class StartActivityContract2(
    private val intent: Intent
) : ActivityResultContract<Map<String, Any?>, Bundle?>() {
    override fun createIntent(context: Context, inputs: Map<String, Any?>?): Intent =
        intent.apply {
            inputs?.forEach {
                when (it.value) {
                    is ArrayList<*> -> {
                        this.putParcelableArrayListExtra(
                            it.key,
                            it.value as java.util.ArrayList<out Parcelable>
                        )
                    }
                    is Parcelable -> this.putExtra(it.key, if (it.value != null) it.value as Parcelable else null)
                    else -> this.putExtra(it.key, if (it.value != null) it.value as Serializable else null)
                }
            }
        }

    override fun parseResult(resultCode: Int, intent: Intent?): Bundle? {
        return when (resultCode) {
            Activity.RESULT_OK -> intent?.extras
            else -> null
        }
    }
}

/*inner class ListDocGlossaryViewHolder(private val binding: UiKitRowPoliceListDocBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(question: InsuranceQuestion) {
        binding.setVariable(BR.question, question)
        Handler(Looper.getMainLooper()).postDelayed({
            if (question.textSearch.isNotEmpty()){
                resetData(question)
                setHighLightedText(binding.rowTitleListDoc,question.textSearch.substring(0, 1)
                    .toUpperCase(Locale.ROOT) + question.textSearch.substring(1))
                setHighLightedText(binding.rowTitleListDocDescription,question.textSearch)
            }else{
                resetData(question)
            } }, 500)
    }

    private fun resetData(question: InsuranceQuestion){
        binding.rowTitleListDoc.text = question.title
        binding.rowTitleListDocDescription.text = question.description
    }
}*/

