package com.example.elacosmetologyandroid.component.buttonSheet

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.GenericBottomSheetOptionsBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.radiobutton.MaterialRadioButton


typealias UIOnOptionItemSelected = (Int) -> Unit


class GenericOptionBottomSheet<T>(
    private val title: String,
    private val itemList: List<T>,
    private var selectPosition: Int,
    private val onOptionItemSelected: UIOnOptionItemSelected
) : BottomSheetDialogFragment() {

    private lateinit var binding: GenericBottomSheetOptionsBinding
    private val listRadios: ArrayList<RadioButton> = ArrayList()

    companion object {
        fun <T> newInstance(
            title: String,
            itemList: List<T>,
            selectPosition: Int,
            onOptionItemSelected: UIOnOptionItemSelected,
        ) =
            GenericOptionBottomSheet(
                title,
                itemList,
                selectPosition,
                onOptionItemSelected
            )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Generic_BottomSheetDialogTheme)
        createRadios()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (itemList.size > 6) {
            val dialog = BottomSheetDialog(requireContext(), theme)
            dialog.setOnShowListener {
                val bottomSheetDialog = it as BottomSheetDialog
                val parentLayout =
                    bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
                parentLayout?.let { default ->
                    val behaviour = BottomSheetBehavior.from(default)
                    setupFullHeight(default)
                    behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
            return dialog
        } else {
            return super.onCreateDialog(savedInstanceState)
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun createRadios() {
        context?.let {
            itemList.forEachIndexed { index, option ->
                val contextWrapper = ContextThemeWrapper(it, R.style.Generic_AppCompatRadioButton)
                val radioButton =
                    MaterialRadioButton(contextWrapper, null, R.style.Generic_AppCompatRadioButton)
                radioButton.id = View.generateViewId()
                radioButton.text = option.toString()
                radioButton.layoutDirection = View.LAYOUT_DIRECTION_LTR
                radioButton.isChecked = selectPosition == index
                val parameters =
                    RadioGroup.LayoutParams(
                        ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        )
                    )
                radioButton.layoutParams = parameters
                radioButton.setPaddingRelative(
                    it.resources.getDimensionPixelSize(R.dimen.size_16),
                    it.resources.getDimensionPixelSize(R.dimen.size_12),
                    it.resources.getDimensionPixelSize(R.dimen.size_16),
                    it.resources.getDimensionPixelSize(R.dimen.size_12)
                )
                radioButton.compoundDrawablePadding =
                    it.resources.getDimensionPixelSize(R.dimen.size_16)
                radioButton.gravity = Gravity.START
                radioButton.setOnClickListener { itemSelected(option, index) }
                listRadios.add(radioButton)
            }
        }
    }

    private fun itemSelected(option: T, position: Int) {
        option.let {
                onOptionItemSelected(position)
                Handler(Looper.getMainLooper()).postDelayed({ this.dismiss() }, 150)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.generic_bottom_sheet_options,
                container,
                false
            )
        setUpView()
        return binding.root
    }

    private fun setUpView() {
        binding.bsTextTitle.text = title
        for (radio in listRadios) {
            binding.bsGroupData.removeView(radio)
            binding.bsGroupData.addView(radio)
        }
        binding.bsBtnClose.setOnClickListener { this.dismiss() }
    }
}