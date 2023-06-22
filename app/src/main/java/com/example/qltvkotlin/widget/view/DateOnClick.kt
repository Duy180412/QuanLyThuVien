package com.example.qltvkotlin.widget.view

import android.app.DatePickerDialog
import android.view.View
import android.widget.TextView
import java.util.Calendar
import com.example.qltvkotlin.domain.model.HasNgayThang
import com.example.qltvkotlin.domain.model.INgayThang
import com.example.qltvkotlin.feature.presentation.extension.cast
import com.example.qltvkotlin.feature.presentation.extension.dateFromString
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateOnClick(private val ngayThang: INgayThang) : View.OnClickListener {
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())


    private fun getDateNow(): Date {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    override fun onClick(v: View?) {
        val initialDateString = ngayThang.cast<HasNgayThang>()?.ngayThang
        val initialDate = getDateFromId(initialDateString)
        val newDate = createNewDate()
        val datePickerDialog = DatePickerDialog(
            v!!.context,
            0,
            { _, year, month, dayOfMonth ->
                newDate.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }
                if (v is TextView) v.text = dateFormat.format(newDate.time)
            },
            initialDate.get(Calendar.YEAR),
            initialDate.get(Calendar.MONTH),
            initialDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun getDateFromId(initialDateString: String?): Calendar {
        val calendar = Calendar.getInstance().apply { clear() }
        val initialDate = initialDateString?.dateFromString() ?: getDateNow()
        calendar.time = initialDate
        return calendar
    }

    private fun createNewDate(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.clear()
        return calendar
    }
}