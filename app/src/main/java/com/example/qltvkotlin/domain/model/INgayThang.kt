package com.example.qltvkotlin.domain.model

import java.util.Calendar
import java.util.Date
interface HasDate {
    fun getDate(): Date?
}
fun getDateNow(): Date {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }
    return calendar.time
}

