package com.example.qltvkotlin.presentation.widget.fields

import com.example.qltvkotlin.R
import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.domain.model.HasDate
import com.example.qltvkotlin.domain.model.IDateField
import com.example.qltvkotlin.domain.model.IHasItemStart
import com.example.qltvkotlin.domain.model.getDateNow
import com.example.qltvkotlin.presentation.extension.dateFromString
import java.util.Date

class SelectDateField(
    fieldsId: FieldsId,
    ngayThang: String = ""
) : InputLayoutField(fieldsId, ngayThang), HasDate, IHasItemStart, IDateField {
    private var date: Date? = ngayThang.dateFromString()
    private var isExpired: Boolean? = date?.let { getDateNow() >= it }
    override val hasItem: Boolean = true
    override var resId: Int = R.drawable.ic_calendar

    override fun update(value: Any?) {
        this.date = value?.toString()?.dateFromString()
        super.update(value)
    }

    override val isValid: Boolean
        get() = this.date != null

    override fun validate(): Boolean {
        if (!isValid) {
            errorValue = "Chưa Đăng Kí Thẻ"
            return false
        }
        if (isValid && isExpired != null && isExpired!!) {
            errorValue = "Đã Hết Hạn Đăng Kí"
            return false
        }

        return date?.let {
            if (it > getDateNow()) {
                true
            } else {
                errorValue = "Hạn mới không thể bé hơn hoặc bằng ngày hiện tại"
                false
            }
        } ?: false
    }

    override fun getDate(): Date? {
        return date
    }
}



