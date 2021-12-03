package com.example.elacosmetologyandroid.component.calendar

import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.elacosmetologyandroid.R
import com.example.elacosmetologyandroid.extensions.gone
import kotlinx.android.synthetic.main.dialog_date_picker.*
import java.util.*

class DatePickerDialog(private val dateDefault: Long, private val dateOld: Long, private val future: Int, private val typeCalendar: Int,
                       private val func: (Pair<Date,Int>) -> Unit) : DialogFragment() {

    private lateinit var selectedDate: Date

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_date_picker, container, false)
    }

    companion object {
        const val MODE_FULL = 1
        const val MODE_SIN_DIA = 2
        const val MODE_SIN_DIA_MES = 3
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var calendar = Calendar.getInstance()
        selectedDate = calendar.time
        val calendarFuture = Calendar.getInstance()
        if (future != 0)calendarFuture.add(Calendar.YEAR, future)
        if (future != 0)datePickerCustom.maxDate = calendarFuture.timeInMillis
        if (dateOld != 0L) datePickerCustom.minDate = dateOld


        if (dateDefault != 0L) {
            val selectedDefault = Calendar.getInstance()
            selectedDefault.time =  Date(dateDefault)
            selectedDate = selectedDefault.time
            calendar = selectedDefault
        }

        when(typeCalendar){
            MODE_SIN_DIA_MES -> {
                txtDayCalendar.gone()
                txtMothCalendar.gone()
                datePickerCustom.findViewById<View>(Resources.getSystem().getIdentifier("day", "id", "android")).visibility = View.GONE
                datePickerCustom.findViewById<View>(Resources.getSystem().getIdentifier("month", "id", "android")).visibility = View.GONE
            }
            MODE_SIN_DIA -> {
                txtDayCalendar.gone()
                datePickerCustom.findViewById<View>(Resources.getSystem().getIdentifier("day", "id", "android")).visibility = View.GONE
            }
        }


        datePickerCustom.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) { _, year, month, dayOfMonth ->
            val selected = Calendar.getInstance()
            selected.set(Calendar.YEAR, year)
            selected.set(Calendar.MONTH, month)
            selected.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            selectedDate = selected.time
        }

        btnCalendarAccept.setOnClickListener {
            func(Pair(selectedDate,typeCalendar))
            dismiss()
        }
        btnCalendarCancel.setOnClickListener { dismiss() }
    }



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
}