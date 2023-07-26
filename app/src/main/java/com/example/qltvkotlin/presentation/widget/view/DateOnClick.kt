package com.example.qltvkotlin.presentation.widget.view

import android.app.DatePickerDialog
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateOnClick(private var date: Date,private var function: (String) -> Unit) : View.OnClickListener {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onClick(v: View?) {
        val initialDate = createCalendarFromDate(date)
        val newDate = createEmptyCalendar()
        val datePickerDialog = DatePickerDialog(
            v!!.context,
            0,
            { _, year, month, dayOfMonth ->
                newDate.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                function.invoke(dateFormat.format(newDate.time))
            },
            initialDate.get(Calendar.YEAR),
            initialDate.get(Calendar.MONTH),
            initialDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun createCalendarFromDate(date: Date): Calendar {
        return Calendar.getInstance().apply { clear() }.apply {
            this.time = date
        }
    }

    private fun createEmptyCalendar(): Calendar {
        return Calendar.getInstance().apply { clear() }
    }
}