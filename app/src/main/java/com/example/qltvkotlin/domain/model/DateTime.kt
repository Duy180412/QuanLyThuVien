package com.example.qltvkotlin.domain.model

import android.util.Log
import com.example.qltvkotlin.feature.presentation.extension.dateFromString
import java.util.Calendar
import java.util.Date

class DataTime(override var ngayThang: String) : Chars(ngayThang), GetDate, HasNgayThang {

    private var date: Date? = ngayThang.dateFromString()
    private var isExpired: Boolean? = date?.let { getDateNow() >= it }

    override fun update(value: Any?) {
        this.date = value?.toString()?.dateFromString()
        super.update(value)
    }

    override val isValid: Boolean
        get() = this.date != null

    override fun validate(): Boolean {
        if (!isValid) {
            error = "Chưa Đăng Kí Thẻ"
            return false
        }
        if (isValid && isExpired != null && isExpired!!) {
            error = "Đã Hết Hạn Đăng Kí"
            return false
        }

        return date?.let {
            if (it > getDateNow()) {
                true
            } else {
                error = "Hạn mới không thể bé hơn hoặc bằng ngày hiện tại"
                false
            }
        } ?: false
    }

    override fun getDate(): Date? {
        return date
    }
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

