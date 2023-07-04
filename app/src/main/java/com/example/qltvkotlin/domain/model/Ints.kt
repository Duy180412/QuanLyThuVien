package com.example.qltvkotlin.domain.model

import android.util.Log

class Ints(private var soLuong: String) : Chars(soLuong) {
    private var max: Int? = null

    override fun update(value: Any?) {
        this.soLuong = value?.toString().orEmpty()
        super.update(value)
    }

    fun setMax(status: String) {
        this.max = status.toIntOrNull()
    }

    override fun validate(): Boolean {
        if (!super.validate()) return false
        if (this.soLuong.toIntOrNull() == null) {
            error = "Hãy Nhấp Số"
            return false
        }
        if (max == null){
            error = "Không Tìm Thấy Số Lượng Tối Đa"
            return false
        }
        if (this.soLuong.toInt() > max!!) {
            error = "Max = $max"
            return false
        }

            return true
    }


}