package com.example.elacosmetologyandroid.component

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.elacosmetologyandroid.R

typealias CrossDialogBlock = ((CrossDialog) -> Unit)?
typealias CrossDialogBlockGeneric = ((CrossDialogGeneric) -> Unit)?
typealias CrossDialogState = ((CrossDialogGeneric) -> Unit)?

class CrossDialog(
    private val layout: Int,
    private var block: CrossDialogBlock = null
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
        block?.invoke(this)
        return view
    }
}


class CrossDialogGeneric(
    private val layout: Int,
    private var block: CrossDialogBlockGeneric = null,
    private val state: CrossDialogState = null
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
        block?.invoke(this)
        return view
    }

    override fun onDestroy() {
        state?.invoke(this)
        super.onDestroy()
    }
}