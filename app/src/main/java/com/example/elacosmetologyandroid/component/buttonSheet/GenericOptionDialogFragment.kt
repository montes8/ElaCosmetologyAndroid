package com.example.elacosmetologyandroid.component.buttonSheet

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.databinding.GenericDialogOptionsRadioBinding
import com.example.elacosmetologyandroid.databinding.RowGenericDialogOptionsBinding

typealias UIOptionDialogItemSelected = (Int) -> Unit


class GenericOptionDialogFragment<T>(
    private val title: String,
    private val listRadios: List<T>,
    private val currentSelectedItem: Int,
    private val uiOptionDialogItemSelected: UIOptionDialogItemSelected
) : DialogFragment(){

    private lateinit var binding: GenericDialogOptionsRadioBinding
    private val adapter = DialogOptionAdapter()


    companion object {
        fun <T> newInstance(
            title: String,
            itemList: List<T>,
            selectPosition: Int,
            onOptionItemSelected: UIOptionDialogItemSelected,
        ) = GenericOptionDialogFragment(title, itemList, selectPosition, onOptionItemSelected)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.generic_dialog_options_radio,
                container,
                false
            )
        setUpView()
        return binding.root
    }

    private fun setUpView(){
        binding.txtTitleOptionGeneric.text = title
        binding.rvListOptionGeneric.adapter = adapter
        adapter.ticketList = listRadios
        binding.ImgTitleOptionClose.setOnClickListener { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val backgroundColor = context?.let { ContextCompat.getColor(
                it, R.color.white
            ) }?.let { ColorDrawable(it) }

            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            dialog.window?.setBackgroundDrawable(backgroundColor)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
    }


    inner class DialogOptionAdapter :
        RecyclerView.Adapter<DialogOptionAdapter.DialogOptionViewHolder>() {

        var ticketList: List<T> = arrayListOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogOptionViewHolder {
            return DialogOptionViewHolder(
                RowGenericDialogOptionsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: DialogOptionViewHolder, position: Int) {
            holder.bind(ticketList[position])
        }

        override fun getItemCount(): Int = ticketList.size

        inner class DialogOptionViewHolder(private val binding: RowGenericDialogOptionsBinding) :
            RecyclerView.ViewHolder(binding.root) {
            fun bind(ticket: T) {
                binding.title = ticket.toString()
                binding.uiChecked = bindingAdapterPosition == currentSelectedItem
                binding.contentOptionRadioList.setOnClickListener {
                    uiOptionDialogItemSelected(bindingAdapterPosition)
                    notifyDataSetChanged()
                    dismiss()
                }
            }
        }
    }
}