package com.example.elacosmetologyandroid.component.snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.extensions.setMargins
import com.google.android.material.card.MaterialCardView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.ContentViewCallback

class SnackBarStatus(
    parent: ViewGroup,
    content: SnackbarStatusView
) : BaseTransientBottomBar<SnackBarStatus>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {
        fun make(
            viewGroup: ViewGroup,
            message: String,
            duration: Int,
            icon: Int,
            backgroundColor: Int,
            upIcon: Boolean,
            marginBottom: Int? = null
        ): SnackBarStatus {
            val snackBarStatusView = LayoutInflater.from(viewGroup.context).inflate(
                R.layout.snackbar_status,
                viewGroup,
                false
            ) as SnackbarStatusView
            snackBarStatusView.setText(message)
            snackBarStatusView.setIconSnack(icon, upIcon)
            snackBarStatusView.setBackgroundColorCard(backgroundColor)
            marginBottom?.let { snackBarStatusView.setMargins(0, 0, 0, it) }
            return SnackBarStatus(viewGroup, snackBarStatusView).apply {
                setDuration(duration)
            }
        }

        fun findSuitableParent(pView: View): ViewGroup? {
            var view: View? = pView
            var fallback: ViewGroup? = null
            do {
                if (view is CoordinatorLayout) {
                    return view
                } else if (view is FrameLayout) {
                    fallback = if (view.getId() == android.R.id.content) {
                        return view
                    } else {
                        view
                    }
                }
                if (view != null) {
                    val parent = view.parent
                    view = if (parent is View) parent else null
                }
            } while (view != null)

            return fallback
        }
    }

}


class SnackbarStatusView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : ConstraintLayout(context, attributeSet, defaultStyle), ContentViewCallback {

    init {
        View.inflate(context, R.layout.card_snack_bar_custom, this)
    }

    fun setText(text: CharSequence?): SnackbarStatusView {
        val textView =
            getViewById(R.id.snackbarCard).findViewById(R.id.snackbarText) as AppCompatTextView
        textView.text = text
        return this
    }

    fun setBackgroundColorCard(backgroundColor: Int): SnackbarStatusView {
        val backgroundCardView =
            getViewById(R.id.snackbarCard) as MaterialCardView
        backgroundCardView.setCardBackgroundColor(
            ContextCompat.getColorStateList(
                context,
                backgroundColor
            )
        )
        return this
    }

    fun setIconSnack(icon: Int, upIcon: Boolean): SnackbarStatusView {
        val snackbarIcon =
            getViewById(R.id.snackbarCard).findViewById(R.id.snackbarIcon) as AppCompatImageView
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            weight = 0f
            gravity = if (upIcon)
                Gravity.TOP
            else
                Gravity.CENTER

        }
        snackbarIcon.layoutParams = params
        snackbarIcon.setImageResource(icon)
        return this
    }


    override fun animateContentIn(delay: Int, duration: Int) {}

    override fun animateContentOut(delay: Int, duration: Int) {}

}