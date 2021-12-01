package com.example.elacosmetologyandroid.component.dialog

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.airbnb.lottie.LottieAnimationView
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.extensions.validateVisibility


class CustomDialog(
    private val layout: Int,
    private var title: String,
    private var description: String,
    private var icon: Int,
    private var imageVisibility: Boolean,
    private var closeVisibility: Boolean,
    private var typeLotti: Int,
    private val func: CustomDialog.() -> Unit
) :
    DialogFragment() {

    lateinit var mView: View


    override fun onStart() {
        super.onStart()
        dialog?.let {
            val backgroundColor = ColorDrawable(
                ContextCompat.getColor(it.context, android.R.color.transparent)
            )
            it.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            it.window?.setBackgroundDrawable(backgroundColor)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        this.mView = view
        dialog?.window?.attributes?.windowAnimations = R.style.DialogTheme
        configActionDialog(this)
        return view
    }

    private fun configActionDialog(dialog: CustomDialog){
        dialog.mView.findViewById<View>(R.id.imgCloseGeneric).validateVisibility(closeVisibility)
        dialog.mView.findViewById<ProgressButton>(R.id.btnCancelDialog).validateVisibility(!imageVisibility)
        if (typeLotti != 0) {
            dialog.mView.findViewById<LottieAnimationView>(R.id.lottieIconGeneric).validateVisibility(
                imageVisibility
            )
            dialog.mView.findViewById<LottieAnimationView>(R.id.lottieIconGeneric).setAnimation(
                if (typeLotti == 1) R.raw.errorpink else R.raw.errorpink
            )
            dialog.mView.findViewById<LottieAnimationView>(R.id.lottieIconGeneric).playAnimation()
            dialog.mView.findViewById<LottieAnimationView>(R.id.lottieIconGeneric).addAnimatorListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    dialog.mView.findViewById<LottieAnimationView>(R.id.lottieIconGeneric).playAnimation()
                }
            })

        }else{
            dialog.mView.findViewById<ImageView>(R.id.dialogIconGeneric).validateVisibility(
                imageVisibility
            )
        }
        dialog.mView.findViewById<View>(R.id.imgCloseGeneric)
            .setOnClickListener { dialog.dismiss() }
        dialog.mView.findViewById<ImageView>(R.id.dialogIconGeneric).setImageResource(icon)
        dialog.mView.findViewById<TextView>(R.id.txtDialogTitle).text = title
        dialog.mView.findViewById<TextView>(R.id.txtDialogMessage).text = description
        dialog.mView.findViewById<ProgressButton>(R.id.btnCancelDialog).setOnClickButtonListener{ dialog.dismiss() }
        dialog.mView.findViewById<ProgressButton>(R.id.btnAcceptDialog).setOnClickButtonListener { func()
            Handler(Looper.getMainLooper()).postDelayed({ dialog.dismiss() }, 200)
        }
    }
}
