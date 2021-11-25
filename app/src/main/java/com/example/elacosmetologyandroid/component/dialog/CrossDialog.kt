package com.example.elacosmetologyandroid.component.dialog

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.elacosmetologyandroid.utils.*
import androidx.fragment.app.DialogFragment
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.component.button.ProgressButton
import com.example.elacosmetologyandroid.extensions.validateVisibility

class CrossDialog(
    private val layout: Int, private var title : String = TITLE_DIALOG_DEFAULT, private var description : String = TITLE_DESCRIPTION_DEFAULT ,
    private var icon : Int = R.drawable.ic_close_pink,private var typeError : Boolean, private val func: CrossDialog.() -> Unit
) :
    DialogFragment() {

    lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Component_CrossDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        this.mView = view
        configActionDialog(this)
        return view
    }

    private fun configActionDialog(dialog: CrossDialog){
        dialog.mView.findViewById<ProgressButton>(R.id.btnCancelDialog).validateVisibility(!typeError)
        dialog.mView.findViewById<ImageView>(R.id.dialogIconGeneric).validateVisibility(typeError)
        dialog.mView.findViewById<View>(R.id.imgCloseGeneric)
            .setOnClickListener { dialog.dismiss() }
        dialog.mView.findViewById<ImageView>(R.id.dialogIconGeneric)
            .setImageResource(icon)
        dialog.mView.findViewById<TextView>(R.id.txtDialogTitle).text = title
        dialog.mView.findViewById<TextView>(R.id.txtDialogMessage).text = description
        dialog.mView.findViewById<ProgressButton>(R.id.btnCancelDialog).setOnClickButtonListener{ dialog.dismiss() }
        dialog.mView.findViewById<ProgressButton>(R.id.btnAcceptDialog).setOnClickButtonListener { func()
            Handler(Looper.getMainLooper()).postDelayed({ dialog.dismiss() }, 500)
        }
    }
}
