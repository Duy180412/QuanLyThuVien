package com.example.qltvkotlin.domain.model

import java.util.Calendar

interface INgayThang

interface HasNgayThang : INgayThang {
    var ngayThang: String
}

interface CalendarOwner {
    val newCalendar: Calendar
        get() = Calendar.getInstance()
}